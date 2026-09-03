package com.example.service.schema;

import com.example.service.DataSourceInitializer;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
class SchemaPerTenantDataSourceConfiguration {

    @Bean
    static SchemaPerTenantDataSourceBeanPostProcessor schemaPerTenantDataSourceBeanPostProcessor() {
        return new SchemaPerTenantDataSourceBeanPostProcessor();
    }

    static class SchemaPerTenantDataSourceBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

        private final AtomicReference<ObjectProvider<DataSourceInitializer>> dataSourceInitializerRef = new AtomicReference<>();

        @Override
        public @Nullable Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (bean instanceof DataSource dataSource) {
                return new SchemaPerTenantDataSource(dataSource,
                        this.dataSourceInitializerRef.get().getIfAvailable());
            }
            return bean;
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.dataSourceInitializerRef.set(beanFactory.getBeanProvider(DataSourceInitializer.class));
        }
    }
}
