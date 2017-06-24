<h1> DataSerializer </h1>

<p>
Serialize and Deserialize data !

<strong> How to use an Serializer ? </strong>

First, create A FileSerializerBuilder object and invoke "get" method to return the FileSerializer:
<br>Example: <span>new FileSerializerBuilder().type(CompilationType.JSON).get();</span>

<strong> How to use an Deserializer ? </strong>

First, create A FileDeserializerBuilder object and invoke "get" method to return the FileSerializer:
<br>Example: <span>new FileDeserializerBuilder().type(CompilationType.JSON).file(new File("data.json").get();</span>

</p>