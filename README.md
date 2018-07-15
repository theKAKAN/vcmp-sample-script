# vcmp-sample-script
VCMP Java plugin sample script - uses gradle

# How to use it?
Clone it and also download the [integration.jar](https://github.com/newk5/vcmp-java-plugin-integration) file and put it under `libs` directory.  
  
After that build it and the JAR file should be made.  

Now you can simply copy the JAR file and run it using the server. Or for testing, you can download the latest [VCMP Server](https://forum.vc-mp.org/?board=4.0) executables and the latest [Java Plugin](https://forum.vc-mp.org/?topic=2574.0).  
You can use [NewK's version](https://github.com/newk5/vcmp-java-plugin) as well, which is currently the latest one.

# Building
Use `gradle build` to make it.  

# Running
There's a `server.cfg` file and a `javaplugin.properties` file as well as a sample script that can be used to quickly test the server. By default, it points to the `build/libs` folder and to version `1.0.0` of the server, which you should change.
