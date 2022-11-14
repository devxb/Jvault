<img src = "https://user-images.githubusercontent.com/62425964/201680890-2072d240-c91d-4b69-94c3-f60c30520e79.svg" width="625" height="auto"/>

# Jvault - Java encapsulation library using DI
   
[한국어로 번역](/KOREAN.md)    
[Learn Jvault]()    
[Java doc]()    
[License]()    
   
![current jvault version](https://img.shields.io/badge/Jvault-0.1-orange) ![test method coverage](https://img.shields.io/badge/Method%20coverage-100%25-brightgreen) ![test line coverage](https://img.shields.io/badge/Line%20coverage-92%25-brightgreen) ![test class coverage](https://img.shields.io/badge/Class%20coverage-91%25-brightgreen) ![needed jdk version](https://img.shields.io/badge/JDK-8-blue) ![libarry status](https://img.shields.io/badge/library%20status-activity-green)    
![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdevxb%2FJvault&count_bg=%23C8C13D&title_bg=%23555555&icon=&icon_color=%23FFFFFF&title=HIT+COUNT&edge_flat=false) ![made with love](https://img.shields.io/badge/Made%20with-Love--❤-red)    
   
Jvault is a library that restricts a class to be accessible only to the class permitted by it and links the dependencies between the restricted classes.   
With Jvaut, you can effectively encapsulate your internal APIs from external users, making it easier to improve the evolveability of your APIs.   

<br>

## Getting Start

This document introduces how to use Jvault and how to download the Jvault library to the project.   
More library details can be found at [Javadoc]() .

<br>
   
## Download Jvault in project

Jvault can be downloaded to the project using Gradle and Maven build tools.

``` gradle
implemetation : org.jvault:jvault-api:0.1
```
   
``` maven
<dependency>
    <groupId>org.jvault</groupId>
    <artifactId>jvault-api</artifactId>
    <version>0.1</version>
    <scope>compile</scope>
</dependency>
```
   
If the project does not use a build tool, you can download __Jvault-x.x.jar__ (x.x is the version) and set the classpath to use Jvault in the project. Also, if you want to apply the Jvault javadoc index to your IDE, download __Jvault-x.x-sources.jar__ and configure it according to the IDE you are using.   

<br>

## Learn Jvault

Learn Jvault introduces the use of Jvault with examples of encapsulating vehicle programs using Jvault.   
   
-_A brief introduction to the example code_   
The Wheel class used in the example is an internal API. If the Wheel class is used outside, it is judged that it will adversely affect the evolution of the API, _(The more people use an API, the harder it is to evolve.)_ so it should not be used outside the project.   
On the other hand, the Car class used in the example is an API. The Car class is intended to be used as an API for users, and all users should use the Car class to communicate with our project.
   
<br>

### Register InternalBean 

Jvault provides @InternalBean annotation to control access of class and mark class as bean registration target.   
Classes marked with @InternalBean annotation are subject to bean scan, and an exception is thrown if a class not permitted by @InternalBean tries to be injected during the DI process.   
   
Let's see how to use it through an example of registering SquareWheel.class, as an InternalBean, which is one of the implementations of the Wheel interface.    
   
``` Java
package usecase.car.wheel;

import org.jvault.annotation.InternalBean;  
import org.jvault.beans.Type;

@InternalBean(
	name = "squareWheel",
	type = Type.SINGLETON,
	accessClasses = {"car.Car"},
	accessPackages = {"car.factory.*", "car.wheel"}
)
public final class SquareWheel implements Wheel{

	SquareWheel(){}
	
	@Override
	public int accel(){
		return 1;
	}
	
}
```
   
The information that can be parametered in @InternalBean is as follows.   

| parameter | value |
|-----------|---|
| name | Specifies the name of the bean to be registered. If omitted, the value obtained by changing the first letter of the class name to lowercase is registered as the name of the bean.   |
| type | Specifies how the bean behaves. If omitted, it is registered as SINGLETON. The scope of type is the same as that of Vault. For example, in the case of a singleton type bean, it is guaranteed that the same instance is always injected within the vault with the same name. However, it has a different address than a bean injected by a different named vault. |
| accessClasses | Specifies the classes that this bean can be injected into. The package path and class name must be specified. If both accessClasses and accessPackages are omitted, all classes can receive this bean. (I chose the designation method of the String type rather than the Class type, because the injected class may not be a public class due to the characteristics of the Internal API.) |
| accessPackages | Specifies the package paths into which this bean can be injected. All classes in the package specified in this parameter can receive this bean injection. The package path must be specified, and if .* expression is used at the end, this bean can be injected up to all subpackages including this package. If the .* expression does not exist, only the corresponding package can receive bean injection. If both accessClasses and accessPackages are omitted, all classes can receive this bean. |
   
Looking at the code block above, the SquareWheel class is a public class that everyone can access, but since the constructor is package-private, no one in another package can instantiate it.    
In other words, the SquareWheel class is encapsulated by the user, but the problem is that it is hidden from code that exists in other packages in the same project that need to use the SquareWheel.   
   
> __WARNING__ : Jvault is not a library that encapsulates classes, but a library that links dependencies of encapsulated classes.    
> Even if it is marked with @InternalBean, the non-encapsulated class can be accessed by everyone.
   
Jvault solves this problem using DI, which reads the information of @InternalBean and throws an exception if the bean is not a class that allows injection.   
   
<br>
   
### Inject InternalBean

Jvault provides @Inject annotation to injected InternalBean.    
There are two methods of injection, constructor injection and field injection, and if both methods coexist in one class, constructor injection is selected.   
   
Let's see how to use it through an example of injecting a wheel into Car, which is one of the implementations of Vehicle, an API that the client will use.   
   
``` Java
package usecase.car;

// Field Inject
public final class Car implements Vehicle{
	
	@Inject("squareWheel")
	private Wheel wheel;
	
	private Car(){}
	
	@Override  
	public String meter() {  
	    return "current meter : " + wheel.accel();  
	}
	
}
```
   
The information that can be parametered in the @Inject annotation is as follows.   
   
| parameter | value |
|-----------|---|
| value | Specifies the name of the bean to inject. |
   
Field injection finds and registers a bean based on the field's name if the value is omitted.   
   
``` Java
package usecase.car;

// Constructor Inject
public final class Car implements Vehicle{
	
	private final Wheel WHEEL;
	
	@Inject
	private Car(@Inject("squareWheel") Wheel wheel){
		WHEEL = wheel;
	}
	
	@Override  
	public String meter() {  
	    return "current meter : " + WHEEL.accel();  
	}
	
}
```
   
> __WARNING__ : In case of constructor injection, @Inject annotation must be marked on the constructor to be used for injection, and @Inject annotation must be marked on the parameters of the constructor. At this time, the name of the bean to be injected must be specified in the @Inject annotation marked on the parameter of the constructor, otherwise an exception is thrown.   
   
> __WARNING__ : Be careful not to cycle between InternalBeans. An exception is thrown when a cycle occurs.   
   
> __TIP__ : The Car class in the example receives bean injection but is not marked with @InternalBean. If the Car class does not need to be injected into other classes, it does not need to be marked with @InternalBean.    
   
<br>
   
### Create Vault, Scan Bean

Vault is a variant of BeanFactory, and plays a role for injecting InternalBeans (registered in Vault) into the passed parameters. Therefore, the parameter passed to the Vault does not need to be an InternalBean, which is why the Car class was not marked as @InternalBean earlier.   
   
> __TIP__ : If the parameter passed to Vault is marked with @InternalBean(type = Type.SINGLETON) and is included in the scope of Vault's bean scan, the same instance is returned for every request. Otherwise, a new object is returned for every request. - This behavior is for ClassVault, and for the behavior of other Vault implementations, refer to [Javadoc]().   
   
Since the word Vault is a bit abstract and the role of Vault may not be well understood, let's take a look at the code that uses Vault in advance.   
   
``` Java
// The created Car instance is the state in which the "sqaureWheel" bean is injected.
ClassVault classVault = TypeVaultFactory.get(buildInfo, VaultType.CLASS);
Car car = classVault.inject(Car.class); 
```
   
The above code receives Car.class as a parameter and returns an instance of Car.class into which the bean is injected. In the code above, a Car instance in which "squareWheel" is injected is returned. This is because the InternalBean named "squareWheel" was injected by the @Inject("squareWheel") annotation marked in the constructor of Car.class earlier.   
   
In this tutorial, we will learn how to create a vault like the one above.
   
First of all, to create a vault, you need to set the location to scan the bean and the information of the vault.   
Bean scan information and vault creation information setting are actually in charge of different objects, but Jvault provides setting using properties file and setting using class which is a way to set the above two information at once for client convenience.   
   
First of all, the settings using the properties file are as follows.   
   
``` properties
org.jvault.vault.name = CAR_VAULT
org.jvault.vault.access.packages = usecase.car, usecase.car.wheel.*
org.jvault.vault.access.classes = usecase.car.Car

org.jvault.reader.packages = usecase.car.*, usecase.wheel
org.jvault.reader.classes = usecase.wheel.SquareWheel, usecase.wheel.RoundWheel
org.jvault.reader.exclude.packages = usecase.car
```
   
| key | value |
| --- | ----- |
| org.jvault.vault.name | Specifies the name of the vault to be created. |
| org.jvault.vault.access.packages | Specifies the package that can receive bean injection through Vault. All classes in the specified package can receive bean injection through the vault, and if the .* expression is used at the end, classes in all sub-packages including the specified package can receive bean injection through the vault. If both org.jvault.vault.access.packages and org.jvault.vault.access.classes are omitted, all classes can receive bean injection through the vault. |
| org.jvault.vault.access.classes | Specifies the class that can receive bean injection through Vault. Only the specified class can receive beans through the vault, and if both org.jvault.vault.access.packages and org.jvault.vault.access.classes are omitted, all classes can receive beans through the vault. |
| org.jvault.reader.packages | Specifies the package in which classes marked with @InternalBean to be registered in the vault exist. When the .* expression is used at the end, the class marked with @InternalBean in all subpackages including the package is registered as a bean in the vault. If there is no expression, all classes marked with @InternalBean in the package are registered as beans in the vault. |
| org.jvault.reader.classes | Specifies the package and class-name of the class marked with @InternalBean to be registered in the vault. |
| org.jvault.reader.exclude.packages | Specifies the path of the package not to be registered as a bean. The registered package is excluded from the bean search, and when the .* expression is used at the end, all subpackages including the package are excluded from the search. However, classes that are directly registered using org.jvault.reader.classes rather than package search are not excluded. |
   
Now you can create a vault using this properties file.     
The following is an example of creating and using ClassVault that takes the Class type as a parameter, injects a dependency into the instance of the parameter, and returns it.

``` Java
// 1. Create instance containing the information of the .properties file.
VaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("path of properties file");

// 2. Get an instance of VaultFactory.
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

// 3. Acquire a Vault using the Vault Factory.
ClassVault vault = vaultFactory.get(buildInfo, VaultType.CLASS);

// 4. Acquire a Car instance using the created vault.
// The created Car instance is the state in which the "sqaureWheel" bean is injected.
Car car = vault.inject(Car.class);
```
   
If you want to create a different type of vault, you can change the value of VaultType passed as an argument of vaultFactory. For the types of VaultTypes that can be selected, refer to [Java doc]().   
   
In the code above, a vault with SquareWheel and RoundWheel registered as beans is created, and the vault can receive Car.class and all classes in the car and wheel package as parameters. Also, since the Car class is specified to be injected with a bean named "squareWheel", a Car instance injected with a bean named "squareWheel" is created by __vault.inject(Car.class);__.   
   
If it is a Vault created by VaultFactory at least once, you can search by name as follows. The vault in the code above and the vault in the code below are different vaults, but the information of the beans stored inside the vault is the same.  
   
``` Java
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

ClassVault vault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);

Car car = vault.inject(Car.class);
```
   
> __TIP__ : Earlier, consider registering SquareWheel Bean as a SINGLETON. The car created in the above example and the car created in the previous example are different objects, but the SquareWheel injected inside the Car is the same object.   
  
The following is an example of creating a vault using the Class setting.   
A VaultConfiguration class can be defined by marking @VaultConfiguration above the class name.   
   
``` Java
@VaultConfiguration(
	name = "CAR_VAULT",
	vaultAccessPackages = {"usecase.car", "usecase.car.*"},
	vaultAccessClasses = {"usecase.car.Car"}
)
public class CarVaultConfig{
	
	@BeanWire
	private SquareWheel squareWheel;
	
}
```
   
information that can be parametered in @VaultConfiguration.   
   
| parameter | value |
| ---------- | ----- | 
| name | Same as org.jvault.vault.name in the properties setting example. If omitted, the value of changing the first letter of the class name to lowercase is registered as the vault name. |
| vaultAccessPackages | Same as org.jvault.vault.access.packages in the properties setting example. If both vaultAccessPackages and vaultAccessClasses are omitted, all classes can be passed as parameters of the vault. |
| vaultAccessClasses | Same as org.jvault.vault.access.classes in the properties setting example. If both vaultAccessPackages and vaultAccessClasses are omitted, all classes can be passed as parameters of the vault. |
   
Also, by marking @BeanWire annotation on member variable inside class, bean to be registered in vault can be defined. At this time, the type of the marked field must be a concrete type, not an interface, and the corresponding class must be marked with @InternalBean. Bean is created according to @InternalBean information of variable class marked with @BeanWire.   
   
_*(I adopted the mapping of BeanWire to field variables. Due to the nature of the internal API, the class constructor may not be public, so it was necessary to register the bean without the constructor.)*_     
   
Now, we can use this class to create a Vault.   
   
``` Java
// 1. Create instance containing the information of the CarVaultConfig.class
VaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(CarVaultConfig.class);

// 2. Get an instance of VaultFactory.
ClassVaultFactory vaultFactory = ClassVaultFactory.getInstance();

// 3. Acquire a Vault using the Vault Factory.
ClassVault vault = vaultFactory.get(buildInfo, VaultType.CLASS);

// 4. Acquire a Car instance using the created vault.
// The created Car instance is in the state in which the "sqaureWheel" bean is injected.
Car car = vault.inject(Car.class);
```
   
For more types of vaults that can be created, refer to [Java doc]().
   
<br>
   
### Use Vault

This tutorials introduce different ways to use Vault.   
Vaults are managed globally by the application, and once a vault is created, the same vault is returned every time from the next request. This means that Vault is globally reusable across applications.   
   
``` Java
public class Main {  
  
    public static void main(String[] args){  
        AnnotationVaultFactoryBuildInfo buildInfo = new AnnotationVaultFactoryBuildInfo(AnnotationConfig.class);  
        TypeVaultFactory typeVaultFactory = TypeVaultFactory.getInstance();  
        typeVaultFactory.get(buildInfo, VaultType.CLASS);  
    }  
  
    @VaultConfiguration(name = "CAR_VAULT", vaultAccessPackages = "usecase.car.*")  
    private static class AnnotationConfig{  
  
        @BeanWire  
        private RoundWheel roundWheel;  
  
        @BeanWire  
        private SquareWheel squareWheel;  
  
    }  
  
}

```
   
For example, as in the code above, if you have created a vault with the name "CAR_VAULT", you can get a vault anywhere in the application as in the code below.   
   
``` Java
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

ClassVault classVault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);

InstanceVault instanceVault = vaultFactory.get("CAR_VAULT", VaultType.INSTANCE);
```
   
_(If the name is the same, you can create various types vault with the same bean.)_   
   
Developers who use Jvault to build APIs may not like the fact that users who use their APIs have to learn Jvault, but Jvault provides the perfect way to solve this "unwelcome third-party code exposure" problem.   
   
For example, if the Car class, which is an API, must be created through a constructor, you can use InstanceVault as follows   
_(When the roundWheel and squareWheel classes are allowed to inject themselves from the InstancedCar,)_   
   
``` Java
package usecase.car;  

public final class InstancedCar implements Vehicle{  
  
    private String carName;  
    @Inject  
    private Wheel roundWheel;  
    @Inject  
    private Wheel squareWheel;  
	
	private InstancedCar(){}
	
    public InstancedCar(String carName){  
        this.carName = carName;  
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();  
        InstanceVault instanceVault = vaultFactory.get("CAR_VAULT", VaultType.INSTANCE);  
        instanceVault.inject(this);  
    }  
	  
    @Override  
    public String meter() {  
        return carName + roundWheel.accel() + squareWheel.accel();  
    }  
}
```
   
Now, InstancedCar users can use InstancedCar injected with roundWheel and squareWheel like this:   
   
``` Java
InstancedCar instancedCar = new InstancedCar("mini-car");
instancedCar.meter();
```
   
In the code above, the user actually gets and uses the InstancedCar object with no knowledge of the Jvault API.   
For more detailed usage of InstanceVault, see [Java doc]().   
   
A better way is to provide a Factory rather than a constructor, or a separate method to get an Instance.   
   
``` Java
public final class VehicleFactory {  
  
    private static final VehicleFactory INSTANCE = new VehicleFactory();  
    private final ClassVault CLASS_VAULT;  
  
    {  
        TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();  
        CLASS_VAULT = vaultFactory.get("CAR_VAULT", VaultType.CLASS);  
    }  
  
    public static <R extends Vehicle> R getVehicle(Class<R> type){  
        return INSTANCE.CLASS_VAULT.inject(type);  
    }  
  
}```
   
Now the user gets a Car object through VehicleFactory like this:   
   
``` Java
	Car car = VehicleFactory.getVehicle(Car.class);  
	InstancedCar instancedCar = VehicleFactory.getVehicle(InstancedCar.class);
```
   
> __TIP__ : I recommend reusing ClassVault every time as in the code above, ClassVault is more efficient because it caches requests that satisfy certain conditions(The class passed as a parameter is marked with @InternalBean(type = Type.SINGLETON) and must be included in the scope of Vault's bean scan.), and uses the cache to process incoming requests after this.   
   
Also, Jvault can be used with spring,   
Register Vault with Spring Bean and inject Vault from an object that requires Vault.   
   
``` Java
@Autowire 
private ClassVault classVault;
```
   
Also, if you need to use Spring Bean in InternalBean, you can use _ApplicationContextAware_ provided by Spring.   
   
<br>
   
### Jvault 확장

The Jvault library provides a way to modify library behavior at runtime.   
All runtime extensions are performed using [JvaultRuntimeExtension.class](). For example, if you want to register your own BeanReader instead of the AnnotatedBeanReader provided by Jvault at default, you can inject the implementation of the BeanReader interface into JvaultRuntimeExtension.   
   
``` Java
BeanReader nullBeanReader = param -> null;  
JvaultRuntimeExtension.extend(nullBeanReader, BeanReader.class);
```
   
As shown in the code above, if you register _nullBeanReader_ that returns null when requested, all Jvault internal implementations that use BeanReader including PropertiesVaultFactoryBuildInfo and AnnotationVaultFactoryBuildInfo will use nullBeanReader.   
If you want to initialize to default, you can write the following code.   
   
``` Java
JvaultRuntimeExtension.reset(BeanReader.class);
// or
JvaultRuntimeExtension.resetAll();
```
   
See [Java doc]() for more detailed Jvault extensions.
   
<h2></h2>

<div align="center">
        <a href="">LICENSE</a> / inquiry : <i>develxb@gmail.com</i> / <a href=""><i>Java doc</i></a>
        <br/><div align="right"> @author : <a href="https://github.com/devxb"><i>devxb</i></a></div>
</div>
