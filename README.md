## Java-Reflection-Parser

### Declaration
```java
public class ExampleVO extends BaseResponse {

  @SerialName("keyName")
  public String keyName;

  public ExampleVO() {}

  public String getKeyName() {
    return keyName;
  }
}
```

### Parsing
```java

String result = "JSON String Value";

ExampleVO exampleVO = new ParsingFactory<ExampleVO>().create(ExampleVO.class, result);
```
