package fr.alib.gotrips.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix="encr")
@ConfigurationPropertiesScan
public class EncryptionUtils {

	@Value("${encr.encryptor-password}")
	private String encryptorPassword;
	
	@Bean
	TextEncryptor encryptor()
	{
		return Encryptors.text(this.encryptorPassword, KeyGenerators.string().generateKey());
	}
	
	
}
