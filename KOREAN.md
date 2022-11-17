<img src = "https://user-images.githubusercontent.com/62425964/201680890-2072d240-c91d-4b69-94c3-f60c30520e79.svg" width="625" height="auto"/>

# Jvault - Java encapsulation library using DI
   
[Translate to English](/README.md)    
[Learn Jvault](#Learn-Jvault)    
[Java doc](https://docs.jvault.org/)    
[License](/LICENSE)    
   
![current jvault version](https://img.shields.io/badge/Jvault-0.1.1-orange) ![test method coverage](https://img.shields.io/badge/Method%20coverage-100%25-brightgreen) ![test line coverage](https://img.shields.io/badge/Line%20coverage-92%25-brightgreen) ![test class coverage](https://img.shields.io/badge/Class%20coverage-90%25-brightgreen) ![needed jdk version](https://img.shields.io/badge/JDK-8-blue) ![libarry status](https://img.shields.io/badge/library%20status-activity-green)    
![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdevxb%2FJvault&count_bg=%23C8C13D&title_bg=%23555555&icon=&icon_color=%23FFFFFF&title=HIT+COUNT&edge_flat=false) ![made with love](https://img.shields.io/badge/Made%20with-Love--❤-red)    
   
Jvault는 클래스를 허가된 클래스와 패키지에서만 사용할 수 있도록 제한하고 제한된 클래스 끼리의 의존성을 연결 하는 라이브러리입니다.   
Jvaut를 사용하면, 내부 API를 외부 사용자로부터 효과적으로 캡슐화할 수 있으며, API의 진화 가능성을 더 쉽게 개선할 수 있습니다.   
   
So, why Jvault?   
   
1, Jvault는 DI 라이브러리로, 구체클래스가 아닌 인터페이스를 기준으로 코드를 작성하도록 도와줍니다.   
   
2, Jvault는 어떠한 라이브러리, 프레임워크에도 의존적이지 않기 때문에, 다양한 개발환경(ex. Spring)에서 함께 사용할 수 있습니다.   
   
3, Jvault는 클래스가 자신을 사용할 수 있는 클래스를 지정하도록 도와주는데, 이러한 특징은 아키텍처를 다음과 같이 만드는 것을 도와줍니다.   
만약, 당신이 라이브러리 개발자 이거나, 다른 코드에서 사용될 코드를 작성하는 API 개발자라면, 라이브러리의 내부 구현을 완벽히 숨긴 채, 의도한 API만 노출할 수 있습니다.    
![Group 31-2](https://user-images.githubusercontent.com/62425964/202201162-30946a77-efd6-451c-ac50-14f293b562f0.svg)    
   
또한, Jvault는 Monolithic 아키텍처로 시작해서 점진적으로 서비스를 분리해 나가길 원하는 상황에서도 유용합니다.    
![Group 30](https://user-images.githubusercontent.com/62425964/202201245-7a723230-a2ab-4ca9-be7a-af965e694765.svg)    
위를 보면, 애플리케이션 서비스들의 내부 구현들은 완벽히 감춰진 채, 각 서비스가 API를 통해 소통하는 것을 볼 수 있습니다.
<br>

## Getting Start

이 문서에서는 Jvault라이브러리를 프로젝트에 다운하는 방법부터 Jvault사용방법을 소개하며, 더 자세한 내용은 (Bean, Vault , VaultFactory and etc.) [Java doc](https://docs.jvault.org/) 에서 확인할 수 있습니다.   
   
<br>
   
## Download Jvault in project

Jvault는 Gradle, Maven 빌드 툴을 이용해 프로젝트에 다운로드할 수 있습니다.

``` gradle
implementation 'org.jvault:jvault-core:0.1.1'
```
   
``` maven
<dependency>
  <groupId>org.jvault</groupId>
  <artifactId>jvault-core</artifactId>
  <version>0.1.1</version>
</dependency>
```
   
만약, 프로젝트에서 별도의 빌드 툴을 사용하지 않는다면 __Jvault-x.x.jar__ (x.x는 버전입니다) 를 다운받고 classpath를 설정해서 Jvault를 프로젝트에서 사용할 수 있습니다. 또한, IDE에 Jvault javadoc 색인을 적용하고 싶은 경우, __Jvault-x.x-sources.jar__ 를 다운받고 사용하는 IDE에 맞게 설정해주세요.   

<br>

## Learn Jvault

Learn Jvault에서는 자동차 프로그램을 Jvault를 이용해 캡슐화하는 예제와 함께 Jvault의 사용법을 소개합니다.   
   
-_예제 코드에 대한 간략한 소개_   
예제에서 사용되는 Wheel 클래스는 내부용 API입니다. Wheel 클래스는 외부에서 사용된다면, API의 진화에 악영향을 미칠 것으로 판단되는 클래스로 _(API는 사용하는 사람이 많을수록 진화하기 힘들어집니다.)_ 프로젝트 외부에서는 사용할 수 없도록 해야 합니다.   
반면, 예제에서 사용되는 Car 클래스는 API입니다. Car 클래스는 프로젝트 외부에서 사용될 목적으로 만들어졌으며, 모든 사용자는 Car 클래스를 이용해서 우리의 프로젝트를 사용해야 합니다.   
   
아래 예제의 전체 코드는 [여기](https://github.com/devxb/Jvault/tree/main/Jvault/src/test/java/usecase/car) 에서 확인할 수 있습니다.
   
<br>

### InternalBean 등록하기

Jvault는 클래스의 접근을 제어하고 클래스를 Bean 등록 대상으로 마킹하기 위해 @InternalBean이라는 어노테이션을 제공합니다.   
@InternalBean어노테이션으로 마킹된 클래스는 빈 스캔의 대상이 되며, DI 과정 중 자신이 허가하지 않은 클래스가 자신을 주입받으려고 시도한다면, 예외를 던집니다.   
   
Wheel 인터페이스의 구현 중 하나인 SquareWheel을 InternalBean으로 등록하는 예시를 통해 사용법을 알아보겠습니다.   
   
``` Java
package usecase.car.wheel;

import org.jvault.annotation.InternalBean;  
import org.jvault.beans.Type;

@InternalBean(
	name = "squareWheel",
	type = Type.SINGLETON,
	accessClasses = {"usecase.car.Car"},
	accessPackages = {"usecase.car.factory.*", "usecase.car.wheel"}
)
public final class SquareWheel implements Wheel{

	SquareWheel(){}
	
	@Override
	public int accel(){
		return 1;
	}
	
}
```
   
@InternalBean에 등록할 수 있는 정보는 아래와 같습니다.   

| parameter | value |
|-----------|---|
| name | 등록될 Bean의 이름을 지정합니다. 생략될 경우 클래스 이름의 첫글자를 소문자로 변경한 값으로 등록됩니다.   |
| type | Bean의 행동 방식을 지정합니다. 생략될 경우 SINGLETON으로 등록됩니다. type의 범위는 Vault범위와 동일합니다. 예를 들어, 싱글톤 타입 Bean의 경우 같은 이름을 갖는 Vault내에서는 항상 같은 주솟값의 객체가 주입됨이 보장되지만, 다른 Vault에 의해 주입되는 Bean과는 다른 주솟값을 갖고 있습니다.|
| accessClasses | 이 빈을 주입받을 수 있는 클래스들을 지정합니다. 패키지 경로와 클래스의 이름을 명시해야하며, 만약, __accessClasses__ 와  __accessPackages__ 모두 생략될 경우 모든 클래스가 이 Bean을 주입받을 수 있습니다. _*(Class 타입이 아닌 String타입의 지정방식을 선택했는데, 그 이유는 Internal API의 특성상 주입받는 클래스가 public class가 아닐수도 있기때문입니다.)*_
| accessPackages | 이 빈을 주입받을 수 있는 패키지 경로들을 지정합니다. 이 파라미터에 명시된 패키지내의 모든 클래스는 이 Bean을 주입받을 수 있습니다. 패키지 경로를 명시해야하며, 마지막에 .* 표현식을 사용할 경우, 해당 패키지를 포함한 모든 하위패키지까지 이 Bean을 주입받을 수 있습니다. 만약, .* 표현식이 존재하지 않는다면, 해당 패키지만 Bean을 주입받을 수 있습니다. 만약, __accessClasses__ 와  __accessPackages__ 모두 생략될 경우 모든 클래스가 이 Bean을 주입받을 수 있습니다. |
   
위 코드 블럭을 보면, SquareWheel클래스는 public class로 모두가 접근할 수 있지만, 생성자가 package-private이므로 다른 패키지에 있는 누구도 인스턴스화 할 수 없습니다.    
즉, SquareWheel클래스는 사용자에게 캡슐화되어있는데, 문제는 SquareWheel을 사용해야 하는 같은 프로젝트의 다른 패키지에 존재하는 코드에도 감춰져 있다는 점 입니다.   
   
> __WARNING__ : Jvault가 클래스를 캡슐화하는 것이 아닌, 캡슐화된 클래스의 의존성을 연결하는 라이브러리임을 주의하세요.    
> @InternalBean으로 마킹되어 있어도, 캡슐화되지 않은 클래스에는 모두가 접근할 수 있습니다.
   
Jvault는 이 문제를 DI를 이용해 해결하는데, @InternalBean에 설정된 정보를 읽고 해당 Bean이 주입을 허가한 클래스가 아니라면 예외를 던집니다.   
   
<br>
   
### 주입받을 InternalBean 마킹하기

Jvault는 등록된 InternalBean을 주입받기 위해 @Inject 어노테이션을 제공합니다.    
주입방식은 생성자 주입과 필드주입이 있으며, 하나의 클래스에 두 개 이상의 방식이 공존할 경우 생성자 주입이 선택됩니다.   
   
클라이언트가 사용할 API인 Vehicle의 구현 중 하나인 Car에 Wheel을 주입하는 예제를 통해 사용법을 알아보겠습니다.   
   
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
   
@Inject어노테이션에 등록할 수 있는 정보는 아래와 같습니다.   
   
| parameter | value |
|-----------|---|
| value | 주입할 빈의 이름을 지정합니다. |
   
필드 주입은 value의 값이 생략되면 필드의 이름을 기준으로 Bean을 찾아서 등록합니다.   
    
생성자 주입의 경우, 주입에 사용할 생성자 메소드 위에 @Inject어노테이션을 마킹해야하며, 생성자의 파라미터에 @Inject어노테이션을 마킹해야 합니다.   
   
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
   
> __WARNING__ : 생성자 주입의 경우 주입에 사용할 생성자 위에 @Inject어노테이션을 마킹 해야 하며, 생성자의 파라미터에 @Inject어노테이션을 마킹 해야 합니다. 이때, 생성자의 파라미터에 마킹된 @Inject 어노테이션에는 주입할 Bean의 이름을 반드시 지정해야 하며, 아니라면 예외가 던져집니다.   
   
> __WARNING__ : @Inject마킹시 빈 끼리 사이클이 생기지 않도록 주의하세요. 사이클이 발견될 경우, 예외가 던져집니다.
   
> __TIP__ : 예시의 Car 클래스는 Bean을 주입받지만 @InternalBean으로 마킹되어있지 않은데, Car 클래스가 다른 클래스에 주입될 필요가 없다면 @InternalBean으로 마킹되어있지 않아도 상관없습니다.    
   
<br>
   
### Vault 생성, Bean스캔

Vault는 BeanFactory의 변종으로 전달된 파라미터에 Vault에 등록된 InternalBean들을 주입하는 역할을 합니다. 따라서, Vault로 전달되는 파라미터는 InternalBean이 아니어도 되는데, 이것이 앞서 Car 클래스를 @InternalBean으로 마킹하지 않은 이유입니다.   
   
> __TIP__ : Vault로 전달되는 파라미터가 @InternalBean(type = Type.SINGLETON)으로 마킹되어있고 Vault의 Bean스캔 범위안에 포함되어 있다면, 요청마다 같은 객체가 반환됩니다. 아니라면 매 요청마다 새로운 객체가 반환됩니다. - 이 동작은 ClassVault에 대한 설명이며, 다른 Vault구현체의 동작방식은 [Javadoc](https://docs.jvault.org/org/jvault/vault/package-summary.html) 을 참조하세요.

Vault라는 단어가 다소 추상적이라 Vault의 역할이 잘 와 닿지 않을 수 있기 때문에, Vault를 사용하는 코드를 미리 살펴보겠습니다.   
   
``` Java
// The created Car instance is the state in which the "sqaureWheel" bean is injected.
ClassVault classVault = TypeVaultFactory.get(buildInfo, VaultType.CLASS);
Car car = classVault.inject(Car.class); 
```
   
위 코드는 Car.class를 파라미터로 받아 Bean이 주입된 Car.class의 인스턴스를 반환하는 코드입니다. 위 코드에서는 "squareWheel"이 주입된 Car 인스턴스가 반환되는데, 이는 앞서 Car.class의 생성자에 마킹해놓은 @Inject("squareWheel") 어노테이션 에 의해서 "squareWheel" 이름의 InternalBean이 주입되었기 때문입니다.   
   
이 목차에서는 위 와 같은 Vault를 만드는 방법을 알아볼 것입니다.
   
우선, Vault를 생성하기 위해 Bean을 스캔할 위치와 Vault의 정보를 설정해줘야 합니다.   
Bean 스캔 정보와 Vault 생성정보 설정은 실제로 각각 다른 객체가 담당하지만, Jvault에서는 클라이언트 편의를 위해 위 두 정보를 한 번에 설정할 수 있는 방법인 properties 파일을 이용한 설정과 Class를 이용한 설정을 제공합니다.   
   
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
   
이제 이 properties 파일을 이용해 Vault를 생성할 수 있습니다.     
아래는 Class 타입을 파라미터로 받아 파라미터의 인스턴스에 의존성을 주입하고 반환하는 ClassVault를 생성하고 사용하는 예시입니다.

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
   
만약, 다른 타입의 Vault를 생성하고 싶다면, vaultFactory의 인자로 전달되는 VaultType의 값을 변경하면 됩니다. 선택할 수 있는 VaultType의 종류는 [Java doc](https://docs.jvault.org/org/jvault/vault/VaultType.html) 을 참조하세요.   
   
위 코드에서는 SquareWheel과 RoundWheel이 Bean으로 등록된 vault가 생성되며, vault는 Car.class와 car, whee l패키지 내의 모든 클래스를 파라미터로 전달받을 수 있습니다. 또한, Car 클래스는 "squareWheel"이름의 빈을 주입받는다고 명시되어 있으므로, __vault.inject(Car.class);__ 에 의해 "squareWheel" 이름의 빈을 주입받은 Car 인스턴스가 생성됩니다.   
   
만약, 한번이라도 VaultFactory에 의해 생성된 Vault라면, 다음과 같이 이름을 통해 조회할 수 있습니다. 위 코드의 vault와 아래 코드의 vault는 서로 다른 주솟값의 vault이지만, vault 내부에 저장된 Bean들의 정보는 모두 똑같습니다.   
   
``` Java
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

ClassVault vault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);

Car car = vault.inject(Car.class);
```
   
> __TIP__ : 앞서, SquareWheel Bean을 SINGLETON으로 등록한 것을 상기하세요. 위 예제에서 생성되는 car과 이전 예제에서 생성한 car은 서로 다른 객체이지만, Car 내부에 주입된 SquareWheel은 같은 객체입니다.   
  
다음은, Class 설정을 이용한 Vault 생성 예시입니다.   
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
   
@VaultConfiguration에 설정 가능한 정보들입니다.   
   
| parameter | value |
| ---------- | ----- | 
| name | properties 설정 예시의 org.jvault.vault.name 과 동일합니다. 만약 생략된다면, 클래스의 이름의 첫 글자를 소문자로 바꾼값이 vault의 이름으로 등록됩니다. |
| vaultAccessPackages | properties 설정 예시의 org.jvault.vault.access.packages 와 동일합니다. 만약, __vaultAccessPackages__ 와 __vaultAccessClasses__ 모두 생략된다면 모든 클래스가 vault의 파라미터로 전달될 수 있습니다. |
| vaultAccessClasses | properties 설정 예시의 org.jvault.vault.access.classes 와 동일합니다. 만약, __vaultAccessPackages__ 와 __vaultAccessClasses__ 모두 생략된다면 모든 클래스가 vault의 파라미터로 전달될 수 있습니다. |
   
또한, 클래스 내부의 멤버 변수에 @BeanWire 어노테이션을 마킹함으로써 Vault에 등록될 Bean을 정의할 수 있습니다. 이때, 마킹된 필드의 타입은 인터페이스가 아닌 구체타입이어야하며, 해당하는 클래스는 반드시 @InternalBean으로 마킹되어 있어야 합니다. Bean은 @BeanWire로 마크된 변수 클래스의 @InternalBean정보에 따라 생성됩니다.   
   
_*(클래스의 필드변수에 BeanWire를 매핑하는 방식을 채택했는데, Internal API의 특성상, 클래스의 생성자가 public이 아닐 수도 있기 때문에, 생성자 없이 Bean을 등록하는 방법이 필요했습니다.)*_     
   
이제, 이 클래스를 이용해 Vault를 생성할 수 있습니다.   
   
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
   
### Vault 사용

이 목차에서는 Vault를 사용하는 다양한 방법을 소개합니다.   
Vault는 애플리케이션에서 전역적으로 관리되며, 한번 생성된 Vault라면, 다음 요청부터는 매번 동등한 Vault가 반환됩니다. 즉, Vault는 애플리케이션에서 전역적으로 재사용할 수 있습니다.   
   
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
   
예를 들어, 위 코드와 같이, "CAR_VAULT"라는 이름으로 Vault를 생성해놓았다면, 애플리케이션의 어디에서든지 아래 코드와 같이 Vault를 얻을 수 있습니다.   
   
``` Java
TypeVaultFactory vaultFactory = TypeVaultFactory.getInstance();

ClassVault classVault = vaultFactory.get("CAR_VAULT", VaultType.CLASS);

InstanceVault instanceVault = vaultFactory.get("CAR_VAULT", VaultType.INSTANCE);
```
   
_(이름이 똑같다면, 동일한 Bean을 가진 다양한 Type의 Vault를 생성할 수 있습니다.)_   
   
Jvault를 사용해서 API를 구축하는 개발자는 자신의 API를 사용하는 유저가 Jvault를 익혀야 하는 상황을 달갑게 여기지 않을 수 있는데, Jvault는 이러한 _"달갑지 않은 제3 코드 노출"_ 문제를 완벽히 해결하는 방법을 제공합니다.   
   
예를 들어, API인 Car 클래스가 반드시 생성자를 통해 생성되어야 한다면, 다음과 같이 InstanceVault를 사용할 수 있습니다.   
_(roundWheel과 squareWheel 클래스가 InstancedCar에서 자신들을 주입받는것을 허락한 상태일때,)_   
   
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
   
이제, InstancedCar 사용자는 다음과 같이 roundWheel과 squareWheel이 주입된 InstancedCar를 사용할 수 있습니다.   
   
``` Java
InstancedCar instancedCar = new InstancedCar("mini-car");
instancedCar.meter();
```
   
위 코드에서 사용자는 실제로 Jvault 라이브러리에 대해 무지한 상태로 InstancedCar객체를 얻고 사용합니다.   
InstanceVault의 더 자세한 사용법은 [Java doc](https://docs.jvault.org/org/jvault/vault/InstanceVault.html) 을 참조하세요.   
   
더 좋은 방법은 생성자가 아닌 Factory를 제공하거나, Instance를 얻는 별도의 메소드(.getInstance(), newInstance())를 제공하는 것입니다.   
   
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
  
}
```
   
이제 사용자는 VehicleFactory를 통해서 Car 객체를 얻을 수 있습니다.   
   
``` Java
Car car = VehicleFactory.getVehicle(Car.class);  
InstancedCar instancedCar = VehicleFactory.getVehicle(InstancedCar.class);
```
   
> __TIP__ : 위 코드와 같이 ClassVault를 매번 재사용하는 방식을 추천하는데, ClassVault는 특정 조건(파라미터로 전달되는 클래스가 @InternalBean(type = Type.SINGLETON)으로 마킹되어있으며, Vault의 Bean스캔 범위에 포함 되어야 함.) 을 만족하는 요청을 캐시하고 이다음부터 들어오는 요청에 대해 캐시된 객체를 반환하기 때문에 더 효율적입니다.
   
또한, Jvault는 스프링과 함께 사용할 수 있는데,   
Spring Bean으로 Vault를 등록하고, Vault가 필요한 객체에서 Vault를 주입받으면 됩니다.   
   
``` Java
@Autowire 
private ClassVault classVault;
```
   
만약, InternalBean에서 Spring Bean을 사용해야 한다면, Spring에서 제공하는 
_ApplicatioContextAware_ 를 이용할 수 있습니다.   
   
<br>
   
### Jvault 확장

Jvault라이브러리는 Runtime에 라이브러리 동작을 수정할 수 있는 방법을 제공합니다.   
모든 Runtime 확장은 [JvaultRuntimeExtension.class](https://docs.jvault.org/org/jvault/extension/JvaultRuntimeExtension.html) 를 이용해 진행되는데, 예를 들어, Jvault가 기본적으로 제공하는 AnnotatedBeanReader가 아닌 자신만의 BeanReader를 등록하고 싶다면, BeanReader 인터페이스의 구현체를 JvaultRuntimeExtension으로 주입하면 됩니다.   
   
``` Java
BeanReader nullBeanReader = param -> null;  
JvaultRuntimeExtension.extend(nullBeanReader, BeanReader.class);
```
   
위 코드와 같이, 요청 시 null을 반환하는 _nullBeanReader_ 를 등록하면, PropertiesVaultFactoryBuildInfo와 AnnotationVaultFactoryBuildInfo를 비롯한 모든 BeanReader를 사용하는 Jvault 내부 구현들이 nullBeanReader를 사용하게 됩니다.   
만약, 기본값으로 초기화하고 싶다면, 다음 코드를 작성하면 됩니다.   
   
``` Java
JvaultRuntimeExtension.reset(BeanReader.class);
// or
JvaultRuntimeExtension.resetAll();
```
   
더 자세한 Jvault 확장에 대한 내용은 [Java doc](https://docs.jvault.org/org/jvault/extension/JvaultRuntimeExtension.html) 을 참조하세요.
   
<h2></h2>

<div align="center">
        <a href="/LICENSE">LICENSE</a> / 문의 : <i>develxb@gmail.com</i> / <a href="https://docs.jvault.org"><i>Java doc</i></a>
        <br/><div align="right"> @author : <a href="https://github.com/devxb"><i>devxb</i></a></div>
</div>
