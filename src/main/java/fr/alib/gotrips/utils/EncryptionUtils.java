package fr.alib.gotrips.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@PropertySource("classpath:credentials.properties")
@Component
@ConfigurationProperties(prefix="encr")
@ConfigurationPropertiesScan
public class EncryptionUtils {

	private String encryptorPassword;
	
	@Bean
	TextEncryptor encryptor()
	{
		return Encryptors.text(this.encryptorPassword, KeyGenerators.string().generateKey());
	}
	
	
}
