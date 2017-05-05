/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at https://github.com/gunterze/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa Healthcare.
 * Portions created by the Initial Developer are Copyright (C) 2012
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See @authors listed below
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package org.dcm4che3.tool.wadors.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.dcm4che3.tool.common.test.TestResult;
import org.dcm4che3.tool.common.test.TestTool;
import org.dcm4che3.tool.wadors.WadoRS;

/**
 * WADO-RS client tool for tests.
 * 
 * @author Hesham Elbadawi <bsdreko@gmail.com>
 */
public class WadoRSTool implements TestTool {

    private ArrayList<String> mediaTypesWithTS = new ArrayList<String>();
    private final String url;
    private final File retrieveDir;
    private WadoRSResult result;

    public WadoRSTool(String url, File retrieveDir) {
        this.url = url;
        this.retrieveDir = retrieveDir;
    }

    public void wadoRS(String testDescription, boolean dumpHeader) throws IOException {
        long t1, t2;
        WadoRS wadors = new WadoRS(getUrl(),getRetrieveDir());
        t1 = System.currentTimeMillis();
        wadors.setAcceptType(getMediaTypesWithTS().toArray(new String[getMediaTypesWithTS().size()]));
        wadors.setDumpHeaders(dumpHeader);
        wadors.wadors();
        t2 = System.currentTimeMillis();
        init(new WadoRSResult(testDescription, t2-t1, wadors.getResponse()));
    }

    /**
     * Add accepted media type and (optional) transfer syntax UID.
     * 
     * @param mediaType
     *            media type (e.g. "application/dicom", "application/dicom+xml",
     *            "application/json", "application/octet-stream",
     *            "image/dicom+jpeg", ...)
     * @param transferSyntax
     *            optional transfer syntax UID to accept (please note that
     *            specifying a transfer syntax UID is not allowed for all media
     *            types)
     */
    public void addAcceptType(String mediaType, String transferSyntax) {
        mediaTypesWithTS.add(mediaType + (transferSyntax != null ? ";" + transferSyntax : ""));
    }

    public ArrayList<String> getMediaTypesWithTS() {
        return this.mediaTypesWithTS;
    }

    public void setMediaTypesWithTS(ArrayList<String> mediaTypesWithTS) {
        this.mediaTypesWithTS = mediaTypesWithTS;
    }

    public String getUrl() {
        return this.url;
    }

    public File getRetrieveDir() {
        return this.retrieveDir;
    }

    public void init(TestResult resultIn) {
        this.result = (WadoRSResult) resultIn;
    }

    public WadoRSResult getResult() {
        return this.result;
    }

}
