javac -h . Lab11.java
javac Gui.java
g++ -c -I"%JAVA_HOME%"\include -I"%JAVA_HOME%"\include\win32 Lab11.cpp -o Lab11.o
g++ -shared -o native.dll Lab11.o -Wl,--add-stdcall-alias

