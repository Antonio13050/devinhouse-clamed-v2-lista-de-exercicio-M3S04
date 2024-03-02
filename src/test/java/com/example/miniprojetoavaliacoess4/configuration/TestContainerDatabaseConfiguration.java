package com.example.miniprojetoavaliacoess4.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = TestContainerDatabaseConfiguration.Initializer.class)
public class TestContainerDatabaseConfiguration {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgreSQLContainer =
                new PostgreSQLContainer<>("postgres:16.1");

        private static void initContainer() {
            Startables.deepStart(Stream.of(postgreSQLContainer)).join();
        }

        private static Map<String, Object> createConnectionConfiguration(){
            return Map.of(
              "spring.datasource.url", postgreSQLContainer.getJdbcUrl(),
              "spring.datasource.username", postgreSQLContainer.getUsername(),
              "spring.datasource.password", postgreSQLContainer.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            /*Inicializa o container que contem o PostgreSQL*/
            initContainer();

            /*Recuperar as configurações de ambiente do contexto do Spring*/
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            /*Criar configuração do testContainers para o Spring reconhecer*/
            MapPropertySource testContainersProperties =
                    new MapPropertySource("testContainer", createConnectionConfiguration());

            /*Adiciona as propriedades do banco dinamicamente*/
            environment.getPropertySources().addFirst(testContainersProperties);
        }
    }
}
