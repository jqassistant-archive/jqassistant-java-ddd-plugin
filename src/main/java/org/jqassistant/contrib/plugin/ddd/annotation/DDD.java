package org.jqassistant.contrib.plugin.ddd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
public @interface DDD {

    /**
     * jQAssistant annotation to mark a Java package as part of the specific Bounded Context. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface BoundedContext {

        /**
         * The name of the Bounded Context.
         *
         * @return The Bounded Contexts name.
         */
        String name();

    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Repository. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Repository {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Value Object. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface ValueObject {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Factory. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Factory {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Service. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Service {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Entity. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Entity {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Aggregate. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface Aggregate {
    }

    /**
     * jQAssistant annotation to mark a Java type as DDD Domain Event. When applied to a package, the annotation is in effect for all contained types.
     *
     * @author stephan.pirnbaum
     */
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.TYPE, ElementType.PACKAGE})
    @interface DomainEvent {
    }

}
