
#Notes

This document represents some notes on the bradleyross/dcm4che fork 
\([Project](https://www.github.com/bradleyross/dcm4che)  
[Documentation](https://bradleyross.github.io/dcm4che) \) of dcm4che/dcm4che.

##Installing jar file

mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file \
-Dfile=dcm4che-log-3.3.9-SNAPSHOT.jar \
-DgroupId=org.dcm4che \
-DartifactId=dcm4che-log \
-Dversion=3.3.9-SNAPSHOT \
-Dpackaging=jar \
-DlocalRepositoryPath=/Users/bradleyross/.m2/repository

##Running Maven

mvn >log.txt clean compile javadoc:aggregate dependency:tree  site

The install goal is required to create the generated sources and XSLT translations.

dependency:copy-dependencies

mvn install

mvn >log.txt clean compile install javadoc:aggregate package  site

mvn >log.txt clean compile install javadoc:aggregate package  site

* clean

* compile

* javadoc:aggregate

* package

* site

##Specifying Artifacts

Artifacts are identified by groupId, artifactId, and version.  Even though a Maven build if you don't specify all of the values properly, you may get results, but they may or may not work tomorrow.  In their documents, IBM frequently used the term "the results are undefined".

I added groupId and versionId values in a number of places in the pom.xml files.  This
has reduced the number of warnings in the output from Maven when I build the application.



##Inheritance and Dependency Management

##Java Syntax Issues and IDE Warnings

If a method implements an interface, it does not receive an override annotation.  There were literally hundreds of these messages.

There is a file module.xml in the module dcm4che-jboss-modules that is not viewed as proper XML.  It is assumed that this file is being processed by another program rather than a normal XML parser.  It contains the entry         ${pegasus.module.dependency}

##Aggregate Javadocs

The goal javadoc:aggregate allows you to have one Javadoc document for the everything under the parent.  (It appears that the run must also use the goal site.)  One of the problems with this is how you specify the location of files since each module has a different base.  However, if you run mvn from the root directory of the parent module, the parameter session.executionRootDirectory will contain the root directory for the parent module.  See the snippet from the parent pom.xml to see how this is done.

The utility of the Javadocs can be increased by linking the Javadoc to the source code and to other Javadoc documents.  This is done by using the tags _linksource_, _links_, and _offlineLinks_ in the snippet below.  Setting _linksource_ to true will link the Javadoc to the source code.  Using _links_ will cause the Javadoc program to read the package-list files from the linked Javadocs and link them to the aggregate Javadoc as required.  The _offlineLinks_ tag is used for packages where the Javadoc program can't read package-list.  (There appear to be a number of possible reasons, some of which seem very arbitrary and possibly based on erroneous security restrictions.)  In this case, the package-list file is stored in the module and the location is specified in the _location_ tag.

       <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-javadoc-plugin</artifactId>
          <version>2.10.4</version>
          <configuration>
            <overview>${session.executionRootDirectory}/src/main/javadoc/overview.html</overview>
            <additionalparam>-Xdoclint:none</additionalparam>
            <quiet>true</quiet>
            <linksource>true</linksource>
            <offlineLinks>
              <offlineLink>
                <url>http://www.javadoc.io/doc/org.apache.camel/camel-core/2.18.3</url>
                <location>${session.executionRootDirectory}/src/main/javadoc/camel/</location>
              </offlineLink>
              <offlineLink>
                <url>http://www.slf4j.org/api/</url>
                <location>${session.executionRootDirectory}/src/main/javadoc/slf4j</location>
              </offlineLink>            
            </offlineLinks>
            <links>
              <link>http://junit.org/junit4/javadoc/latest/</link>
            </links>
            <failOnError>false</failOnError>
          </configuration>
        </plugin>

##Test Packages

Packages involving junit tests should probably be under src/test/java rather than src/main/java.  Look at src/main/java packages having a last part of **test**.

##Java Package Locations

When dealing with a project composed of multiple modules, each package is normally assigned to a single module.  This makes code maintenance easier.  One of the packages is split between two modules.

##Logging Frameworks

I am receiving the following error.  This is presumably in module dcm4che-conf-core.  (Error was repeated 5 times.)

    log4j:WARN No appenders could be found for logger (org.dcm4che3.conf.core.storage.SingleJsonFileConfigurationStorage).
    log4j:WARN Please initialize the log4j system properly.
    log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    
    http://stackoverflow.com/questions/12458469/slf4j-failed-to-load-class-org-slf4j-impl-staticloggerbinder-in-a-maven-proj

##dcm4che-all


#References

## Markdown Syntax

"Markdown Cheatsheet" from GitHub site for Markdown [GitHub](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

"Markdown Quick Reference" from WordPress [Wordpress](https://en.support.wordpress.com/markdown-quick-reference/)

"Markdown Syntax Guide" from Confluence [Confluence](https://confluence.atlassian.com/bitbucketserver/markdown-syntax-guide-776639995.html) 

[Markdown Tutorial](http://www.markdowntutorial.com)

##Maven Plugins

[Available Plugins](http://maven.apache.org/plugins/index.html)

[Site Descriptor](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html)

[Configuring Reports](https://maven.apache.org/plugins/maven-site-plugin/examples/configuring-reports.html)

[dependency:tree](http://maven.apache.org/plugins/maven-dependency-plugin/tree-mojo.html)




