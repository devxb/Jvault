<img src = "https://user-images.githubusercontent.com/62425964/201114722-23e84cd7-eb57-4f9b-a4a5-f218cc480dcf.svg" width="630" height="auto"/>

# Jvault - Java encapsulation library using DI
   
[Translate to English]()    
[Learn Jvault]()    
[Java doc]()    
[License]()    
   
![current jvault version](https://img.shields.io/badge/Jvault-0.1-orange) ![test method coverage](https://img.shields.io/badge/Method%20coverage-99%25-brightgreen) ![test code coverage](https://img.shields.io/badge/Line%20coverage-91%25-brightgreen) ![needed jdk version](https://img.shields.io/badge/JDK-8-blue) ![libarry status](https://img.shields.io/badge/library%20status-activity-green)    
![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdevxb%2FJvault&count_bg=%23C8C13D&title_bg=%23555555&icon=&icon_color=%23FFFFFF&title=HIT+COUNT&edge_flat=false) ![made with love](https://img.shields.io/badge/Made%20with-Love--❤-red)    
   
Jvault는 Bean을 허가된 클래스 에서만 접근가능하도록 제한하고 제한된 클래스 끼리의 의존성을 연결 하는 라이브러리 입니다.   
Jvaut를 사용하면, 내부 API를 외부 사용자로부터 효과적으로 캡슐화 할 수 있으며, API의 진화가능성을 더 쉽게 향상시킬 수 있습니다.   

<br>

## Getting Start

이 문서에서는 Jvault라이브러리를 프로젝트에 다운하는 방법부터 Jvault사용방법을 소개합니다.   
선택가능한 더 많은 (Bean, Vault , VaultFactory and etc.) 종류는 [Java doc]() 에서 확인할 수 있습니다.

<br>
   
## Download Jvault in project

Jvault는 Gradle, Maven 빌드툴을 이용해 프로젝트에 다운로드 할 수 있습니다.

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
   
만약, 프로젝트에서 별도의 빌드 툴을 사용하지 않는다면 __Jvault-x.x.jar__ (x.x는 버전입니다) 를 다운받고 classpath를 설정해서 Jvault를 프로젝트에서 사용할 수 있습니다. 또한, IDE에 Jvault javadoc 색인을 적용하고 싶은 경우, __Jvault-x.x-sources.jar__ 를 다운받고 사용하는 IDE에 맞게 설정해주세요.   

<br>

## Learn Jvault

Learn Jvault에서는 자동차 프로그램을 Jvault를 이용해 캡슐화 하는 예제와 함께 Jvault의 사용법을 소개합니다.   
   
_예제 코드에 대한 간략한 소개_   
예제에서 사용되는 Wheel 클래스는 내부용 API 입니다. 만약 Wheel을 외부에서 사용한다면, API의 진화에 큰 악영향을 미칠것으로 _(API는 사용하는 사람이 많을수록 진화하기 힘들어집니다.)_ 프로젝트 외부에서는 사용할 수 없도록 해야합니다.   
반면, 예제에서 사용되는 Car 클래스는 API 입니다. Car 클래스는 프로젝트 외부에서 사용될 목적으로 만들어 졌으며, 모든 사용자는 Car 클래스를 이용해서 우리의 프로젝트와 소통해야합니다.
   
<br>

### InternalBean 등록하기

Jvault는 클래스의 접근을 제어하기위해 @InternalBean이라는 어노테이션을 제공합니다.   
@InternalBean어노테이션으로 마킹된 클래스는 빈 스캔의 대상이 되며, DI 과정중 자신이 허가하지 않은 클래스가 자신을 주입받으려고 시도한다면, 예외를 던집니다.   
   
내부용 API인 Wheel인터페이스의 구현중 하나인 SquareWheel을 InternalBean으로 등록하는 예시를 통해 사용법을 알아보겠습니다.   
   
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
   
@InternalBean에 등록할수있는 정보는 아래와 같습니다.   

| parameter | value |
|-----------|---|
| name | 등록될 Bean의 이름을 지정합니다. 생략될 경우 클래스 이름의 첫글자를 소문자로 변경한 값으로 등록됩니다.   |
| type | Bean의 행동 방식을 지정합니다. 생략될 경우 SINGLETON으로 등록됩니다. type의 범위는 Vault범위와 동일합니다. 예를 들어, 싱글톤 타입 Bean의 경우 같은 Vault내에서는 항상 같은 주솟값의 객체가 주입됨이 보장되지만, 다른 Vault에 의해 주입되는 Bean과는 다른 주솟값을 갖고 있습니다.|
| accessClasses | 이 빈을 주입받을 수 있는 클래스들을 지정합니다. 패키지 경로와 클래스의 이름을 명시해야하며, 만약, __accessClasses__ 와  __accessPackages__ 모두 생략될 경우 모든 클래스가 이 Bean을 주입받을 수 있습니다. _*(Class 타입이 아닌 String타입의 지정방식을 선택했는데, 그 이유는 Internal API의 특성상 주입받는 클래스가 public class가 아닐수도 있기때문입니다.)*_
| accessPackages | 이 빈을 주입받을 수 있는 패키지 경로들을 지정합니다. 이 파라미터에 명시된 패키지내의 모든 클래스는 이 Bean을 주입받을 수 있습니다. 패키지 경로를 명시해야하며, 마지막에 .* 표현식을 사용할 경우, 해당 패키지를 포함한 모든 하위패키지까지 이 Bean을 주입받을 수 있습니다. 만약, .* 표현식이 존재하지 않는다면, 해당 패키지만 Bean을 주입받을 수 있습니다. 만약, __accessClasses__ 와  __accessPackages__ 모두 생략될 경우 모든 클래스가 이 Bean을 주입받을 수 있습니다. |
   
위 코드블럭을 보면, SquareWheel클래스는 public class로 모두가 접근할 수 있지만, 생성자가 package-private이므로 다른 패키지에있는 누구도 인스턴스화 할 수 없습니다.    
즉, SquareWheel클래스는 사용자에게 캡슐화 되어있는데, 문제는 SquareWheel을 사용해야하는 다른 패키지에 존재하는 프로젝트내의 코드에도 감춰져있다는점 입니다.   
   
> __WARNING__ : Jvault가 클래스를 캡슐화 하는것이 아닌, 캡슐화된 클래스의 의존성을 연결하는 라이브러리 임을 주의하세요.    
> @InternalBean으로 마킹되어 있어도, 캡슐화 되지 않은 클래스에는 모두가 접근할 수 있습니다.
   
Jvault는 이 문제를 DI를 이용해 해결하는데, @InternalBean에 설정된 정보를 읽고 해당 Bean이 주입을 허가한 클래스가 아니라면 예외를 던집니다.   
   
<br>
   
### 주입받을 InternalBean 마킹하기

Jvault는 등록된 InternalBean을 주입받기 위해 @Inject 어노테이션을 제공합니다.    
주입방식은 생성자 주입과 필드주입이 있으며, 하나의 클래스에 두방식이 공존할 경우 생성자 주입이 선택됩니다.   
   
클라이언트가 사용할 API인 Vehicle의 구현중 하나인 Car에 Wheel을 주입하는 예제를 통해 사용법을 알아보겠습니다.   
   
``` Java
package usecase.car;

// Field Inject
public final class Car implements Vehicle{
	
	@Inject("squareWheel")
	private Wheel wheel;
	
	private Vehicle(){}
	
	@Override  
	public String meter() {  
	    return "current meter : " + wheel.accel();  
	}
	
}
```
   
@Inject어노테이션에 등록할 수 있는 정보는 아래와 같습니다.   
   
| parameter | value |
|-----------|---|
| value | 주입할 빈의 이름을 지정합니다. |
   
필드 주입은 value의 값이 생략되면 필드의 이름을 기준으로 Bean을 찾아서 등록합니다.   
   
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
   
> __WARNING__ : 생성자 주입의 경우 주입에 사용할 생성자 위에 @Inject어노테이션을 마킹 해야하며, 생성자의 파라미터에 @Inject어노테이션을 마킹 해야합니다. 이때, 생성자의 파라미터에 마킹된 @Inject 어노테이션에는 주입할 Bean의 이름을 반드시 지정해야 하며, 아니라면 예외가 던져집니다.   
   
> __WARNING__ : @Inject마킹시 빈 끼리 사이클이 생기지 않도록 주의하세요. 사이클이 발견될 경우, 예외가 던져집니다.
   
> __TIP__ : 예시의 Car클래스는 Bean을 주입받지만 @InternalBean으로 마킹되어있지 않은데, Car클래스가 다른 클래스에 주입될 필요가 없다면 @InternalBean으로 마킹되어있지 않아도 상관없습니다.    
> 만약, Car클래스가 @InternalBean(type = Type.SINGLETON)으로 마킹되어 있고, 빈 스캔 범위에 포함되어있다면, Vault를 이용해 Inject될때마다 같은 객체가 주입됩니다. 
   
<br>
   
### Bean 스캔, Vault 생성, 의존성 주입

Vault는 BeanFactory의 일종으로 연결된 Internal Bean들을 API에 주입하는 역할을 합니다. 또한, Bean들은 Vault를 생성하는 과정중 스캔되고 연결됩니다.    
따라서, 이 목차에서는 @InternalBean이 위치한 경로지정부터 생성될 Vault의 정보를 설정하는 방법과 Vault를 이용해 의존성을 주입하는 방법을 알아볼 것 입니다.   
   
Bean 스캔정보와 Vault 생성정보 설정은 실제로 각각 다른 객체가 담당하지만, Jvault에서는 클라이언트 편의를 위해 위 두 정보를 한번에 설정할 수 있는 방법인 properties 파일을 이용한 설정과 Class를 이용한 설정을 제공합니다.   
   
우선, properties 파일을 이용한 설정은 아래와 같습니다.   
   
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
| org.jvault.vault.name | 생성될 Vault의 이름을 명시합니다. |
| org.jvault.vault.access.packages | Vault를 통해 Bean을 주입받을수 있는 패키지를 명시합니다. 명시된 패키지내의 모든 클래스가 Vault를 통해 Bean을 주입받을수 있으며, 마지막에 .* 표현식이 사용될 경우, 명시된 패키지를 포함한 하위의 모든 패키지내의 클래스가 Vault를 통해 Bean을 주입받을수 있습니다. 만약, __org.jvault.vault.access.packages__ 와 __org.jvault.vault.access.classes__ 둘 모두 생략되었다면, 모든 클래스가 Vault를 통해 Bean을 주입받을수 있습니다. |
| org.jvault.vault.access.classes | Vault를 통해 Bean을 주입받을수 있는 클래스를 명시합니다. 명시된 클래스만 Vault를 통해 Bean을 주입받을수 있으며, 만약, __org.jvault.vault.access.packages__ 와 __org.jvault.vault.access.classes__ 둘 모두 생략되었다면, 모든 클래스가 Vault를 통해 Bean을 주입받을수 있습니다. |
| org.jvault.reader.packages | Vault에 등록될 @InternalBean으로 마킹된 클래스가 존재하는 패키지를 명시합니다. 마지막에 .* 표현식이 사용될 경우, 해당 패키지를 포함한 모든 하위 패키지안의 @InternalBean으로 마킹된 클래스가 Vault에 Bean으로 등록됩니다. 표현식이 없다면, 해당 패키지내의 @InternalBean으로 마킹된 모든 클래스가 Vault에 Bean으로 등록됩니다. |
| org.jvault.reader.classes | Vault에 등록될 @InternalBean으로 마킹된 클래스의 패키지와 이름을 명시합니다. |
| org.jvault.reader.exclude.packages | Bean으로 등록하지 않을 패키지의 경로를 명시합니다. 등록되어있는 패키지는 Bean탐색에서 제외되며, 마지막에 .* 표현식이 사용될경우, 해당 패키지를 포함한 모든 하위패키지를 탐색에서 제외합니다. 단, 패키지 탐색이 아닌, __org.jvault.reader.classes__ 를 이용해 직접 등록되는 클래스는 제외되지 않습니다. |
   
이제 이 properties파일을 이용해 Vault를 생성할 수 있습니다.     
아래는 Class 타입을 파라미터로 받아 파라미터의 인스턴스에 의존성을 주입하고 반환하는 ClassVault를 생성하고 사용하는 예시입니다.

``` Java
// 1. Create instance containing the information of the .properties file.
VaultFactoryBuildInfo buildInfo = new PropertiesVaultFactoryBuildInfo("path of properties file");

// 2. Get an instance of VaultFactory.
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

// 3. Acquire a Vault using the Vault Factory.
ClassVault vault = vaultFactory.get(buildInfo, VaultType.CLASS);

// 4. Acquire a Car instance using the created vault.
// The created Car instance is in the state in which the "sqaureWheel" bean is injected.
Car car = vault.inject(Car.class);
```
   
만약, 다른 타입의 Vault를 생성하고 싶다면, vaultFactory의 인자로 전달되는 VaultType의 값을 변경하면 됩니다. 선택할 수 있는 VaultType의 종류는 [Java doc]() 을 참조하세요.
   
위 코드에서는 SquareWheel과 RoundWheel이 Bean으로 등록된 vault가 생성되며, vault는 Car.class와 car, wheel패키지 내의 모든 클래스를 파라미터로 전달받을수 있습니다. 또한, Car 클래스는 "squareWheel"이름의 빈을 주입받는다고 명시되어 있으므로, __vault.inject(Car.class);__ 에 의해 "squareWheel" 이름의 빈을 주입받은 Car 인스턴스가 생성됩니다.   
   
만약, 한번 이라도 VaultFactory에 의해 생성된 Vault라면, 다음과 같이 이름을 통해 조회할 수 있습니다. 위 코드의 vault와 아래 코드의 vault는 서로 다른 주솟값의 vault이지만, vault내부에 저장된 Bean들의 정보는 모두 똑같습니다.  
   
``` Java
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

ClassVault vault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);

Car car = vault.inject(Car.class);
```
   
> TIP : 앞서, SquareWheel Bean을 SINGLETON으로 등록한 것을 상기하세요. 위 예제에서 생성되는 car과 이전 예제에서 생성한 car은 서로 다른 객체이지만, Car 내부에 주입된 SquareWheel은 같은 객체 입니다.   
> 만약, Car클래스가 @InternalBean(type = Type.SINGLETON)으로 매핑되어 있으며, Bean스캔범위에 포함되어 있다면, 매번 같은 Car객체가 반환됩니다.
   
  
다음은, Class를 이용한 설정 예시입니다.   
Vault설정 클래스는 클래스 이름 위에 @VaultConfiguration을 마킹함으로 정의할 수 있습니다.
   
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
   
@VaultConfiguration에 설정가능한 정보들 입니다.   
   
| parameter | value |
| ---------- | ----- | 
| name | properties 설정 예시의 org.jvault.vault.name 과 동일합니다. 만약 생략된다면, 클래스의 이름의 첫 글자를 소문자로 바꾼값이 vault의 이름으로 등록됩니다. |
| vaultAccessPackages | properties 설정 예시의 org.jvault.vault.access.packages 와 동일합니다. 만약, __vaultAccessPackages__ 와 __vaultAccessClasses__ 모두 생략된다면 모든 클래스가 vault의 파라미터로 전달될 수 있습니다. |
| vaultAccessClasses | properties 설정 예시의 org.jvault.vault.access.classes 와 동일합니다. 만약, __vaultAccessPackages__ 와 __vaultAccessClasses__ 모두 생략된다면 모든 클래스가 vault의 파라미터로 전달될 수 있습니다. |
   
또한, 클래스 내부의 멤버변수에 @BeanWire 어노테이션을 마킹함으로써 Vault에 등록될 Bean을 정의할 수 있습니다. 이때, 마킹된 필드의 타입은 인터페이스가 아닌 구체타입 이어야 하며, 해당하는 클래스는 반드시 @InternalBean으로 마킹되어 있어야 합니다. Bean은 @BeanWire와 매칭되는 클래스의 @InternalBean정보에 따라 생성됩니다.   
   
_*(멤버변수에 BeanWire를 매핑하는 방식을 채택했는데, Internal API의 특성상, 클래스의 생성자가 public이 아닐수도 있기때문에, 생성자없이 Bean을 등록하는 방법이 필요했습니다.)*_
   
이제, 이 클래스를 이용해 Vault를 생성할수 있습니다.   
   
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
   
<br>
   
### 응용


여기까지, Jvault사용법에 대한 소개가 끝났습니다.
   

<h2></h2>

<div align="center">
        <a href="">LICENSE</a> / 문의 : <i>develxb@gmail.com</i> / <a href=""><i>Java doc</i></a>
        <br/><div align="right"> @author : <a href="https://github.com/devxb"><i>devxb</i></a></div>
</div>
