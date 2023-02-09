plugins {
	java
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.commons:commons-crypto:1.1.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
