plugins {
	java
	id("org.springframework.boot") version "2.5.14"
	id("io.spring.dependency-management") version "1.1.7"
	jacoco
}

group = "com.bci.exercise"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}

jacoco {
	toolVersion = "0.8.8"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.validation:jakarta.validation-api:2.0.2")
	implementation("org.hibernate.validator:hibernate-validator:6.0.13.Final")
	runtimeOnly("com.h2database:h2")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("jakarta.validation:jakarta.validation-api:3.1.1")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.mapstruct:mapstruct:1.6.3")
	implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
	implementation("org.springframework.boot:spring-boot-starter-json")
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required.set(true)
		html.required.set(true)
	}

	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude(
					"com/bci/exercise/user_authentication/constants/**",
					"com/bci/exercise/user_authentication/dto/**",
					"com/bci/exercise/user_authentication/exception/**",
					"com/bci/exercise/user_authentication/mapper/**",
					"com/bci/exercise/user_authentication/model/**",
					"com/bci/exercise/user_authentication/repository/**",
					"com/bci/exercise/user_authentication/security/SecurityConfig.class",
					"com/bci/exercise/user_authentication/security/WebSecurityConfig.class",
				)
			}
		})
	)
}
