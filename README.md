<h1> DataSerializer </h1>

<p>
Serialize and Deserialize data !

<strong> How to use an Serializer ? </strong>

First, create A FileSerializerBuilder object and invoke "get" method to return the FileSerializer:
<br> Example: <br>
```java
// Create first an builder with the compilation type and cast it
JsonFileSerializer serializer = (JsonFileSerializer) new FileSerializerBuilder().type(CompilationType.JSON).get();
// write your data, Warning: the json serializer need keys !
serializer.writeObject("int", 192);
serializer.writeObject("string", "test");
serializer.writeObject("float", 42.5f);
// and for creating the file, juste call "compile"
// method with the target file
long execTime = serializer.compile(new File("data")); // You can get the time of the building task
// Don't forget to close the serializer ! You can use a "try-catch-with-ressources".
serializer.close();
```
<br>

<strong> How to use an Deserializer ? </strong>

First, create A FileDeserializerBuilder object and invoke "get" method to return the FileSerializer:
<br> Example: <br>
```java
// Create first an builder with the compilation type and the file and cast it
JsonFileDeserializer deserializer = (JsonFileDeserializer) new FileDeserializerBuilder().type(CompilationType.JSON).file(new File("data")).get();
// read your data, the Deserializer has DataInputStream as superclass, Warning: the json deserializer need keys !
int resultInt = deserializer.readInt("int");
String resultString = deserializer.readUTF("string");
float resultFloat = deserializer.readFloat("float");
// Don't forget to close the deserializer ! You can use a "try-catch-with-ressources".
serializer.close();
```
<br>

<strong>TODO</strong>
<ul>
<li>Create MavenCentral Repo<li>
</ul>

</p>
