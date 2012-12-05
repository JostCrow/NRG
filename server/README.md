Install Glassfish OR Tomcat (We are use Glassfish)

--Eclipse
Go to File -> New -> Project, selected "Dynamic Web Project" in the "New Dynamic Web Project" dialog
Select the folder
Add de web-server.

--Netbeans
Create new project
Select java web
Select web application from existing source
Select the folder
Chose webserver
click finish

--Stand alone
----Glassfish
Before You Begin

The sample application must be available before you start this task. To download the sample, see Obtaining a Sample Application. At least one GlassFish Server domain must be started before you deploy the sample application.

Launch the Administration Console by typing the following URL in your browser:

http://localhost:4848

Click the Applications node in the tree on the left.

The Applications page is displayed.

Click the Deploy button.

The Deploy Applications or Modules page is displayed.

Select Packaged File to be Uploaded to the Server, and click Browse.

Navigate to the location in which you saved the AsaServer.war file, select the file, and click Open.

Accept the other default settings, and click OK.

You are returned to the Applications page.

Select the check box next to the application and click the Launch link to run the application.