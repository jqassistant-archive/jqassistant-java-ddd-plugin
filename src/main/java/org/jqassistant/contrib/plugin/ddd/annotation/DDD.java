package org.jqassistant.contrib.plugin.ddd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Representation of the DDD concepts.
 *
 * @author stephan.pirnbaum
 */
@Target({})
public @interface DDD {

    /**
     * jQAssistant annotation to mark a Java package as part of the specific Bounded Context. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface BoundedContext {

        /**
         * The name of the {@link BoundedContext}.
         *
         * @return The Bounded Contexts name.
         */
        String name();

        /**
         * The name of the {@link BoundedContext}s this {@link BoundedContext} may depend on.
         *
         * @return All {@link BoundedContext}s to which dependencies are allowed.
         */
        String[] dependsOn() default {};

    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Repository. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Repository { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Value Object. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface ValueObject { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Factory. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Factory { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Service. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Service { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Entity. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Entity { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Aggregate. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Aggregate { }

    /**
     * jQAssistant annotation to mark a Java type as DDD Domain Event. When applied to a package, the annotation is in effect for all contained types.
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface DomainEvent { }

    /**
     * Representation of the DDD Layer concept.
     */
    @Target({})
    @interface Layer {

        /**
         * jQAssistant annotation to mark a Java type as part of the Interface Layer. When applied to a package, the annotation is in effect for all contained types.
         */
        @Retention(RetentionPolicy.CLASS)
        @Target({ElementType.TYPE, ElementType.PACKAGE})
        @interface InterfaceLayer {}

        /**
         * jQAssistant annotation to mark a Java type as part of the Application Layer. When applied to a package, the annotation is in effect for all contained types.
         */
        @Retention(RetentionPolicy.CLASS)
        @Target({ElementType.TYPE, ElementType.PACKAGE})
        @interface ApplicationLayer {}

        /**
         * jQAssistant annotation to mark a Java type as part of the Domain Layer. When applied to a package, the annotation is in effect for all contained types.
         */
        @Retention(RetentionPolicy.CLASS)
        @Target({ElementType.TYPE, ElementType.PACKAGE})
        @interface DomainLayer {}

        /**
         * jQAssistant annotation to mark a Java type as part of the Infrastructure Layer. When applied to a package, the annotation is in effect for all contained types.
         */
        @Retention(RetentionPolicy.CLASS)
        @Target({ElementType.TYPE, ElementType.PACKAGE})
        @interface InfrastructureLayer {}

    }
}
