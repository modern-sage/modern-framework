package com.modernframework.core.utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Unit tests for ClassUtils class
 */
public class ClassUtilsTest {

    @Test
    public void testIsAssignableFrom() {
        // Test with null values
        assertFalse(ClassUtils.isAssignableFrom(null, String.class));
        assertFalse(ClassUtils.isAssignableFrom(String.class, null));
        assertFalse(ClassUtils.isAssignableFrom(null, null));

        // Test with equal classes
        assertTrue(ClassUtils.isAssignableFrom(String.class, String.class));

        // Test with assignable classes
        assertTrue(ClassUtils.isAssignableFrom(CharSequence.class, String.class));
        assertFalse(ClassUtils.isAssignableFrom(String.class, CharSequence.class));
        assertTrue(ClassUtils.isAssignableFrom(Object.class, String.class));
    }

    @Test
    public void testIsStatic() throws NoSuchMethodException {
        // Test with a static method
        Method staticMethod = Math.class.getMethod("abs", int.class);
        assertTrue(ClassUtils.isStatic(staticMethod));

        // Test with a non-static method
        Method nonStaticMethod = String.class.getMethod("length");
        assertFalse(ClassUtils.isStatic(nonStaticMethod));

        // Test with null method (should throw exception due to Asserts.notNull)
        try {
            ClassUtils.isStatic(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testSetAccessible() throws NoSuchMethodException {
        // Test with a private method
        Method privateMethod = String.class.getDeclaredMethod("charAt", int.class);
        assertFalse(privateMethod.isAccessible());

        // Make it accessible using ClassUtils
        ClassUtils.setAccessible(privateMethod);
        assertTrue(privateMethod.isAccessible());

        // Test with null
        ClassUtils.setAccessible(null);
    }

    @Test
    public void testIsAssignableWithArrays() {
        Class<?>[] fromClasses = new Class[]{String.class};
        Class<?>[] toClasses = new Class[]{String.class};
        Class<?>[] toClasses2 = new Class[]{Integer.class};
        Class<?>[] differentLength = new Class[]{String.class, Integer.class};
        
        assertTrue(ClassUtils.isAssignable(fromClasses, toClasses, false));
        assertTrue(ClassUtils.isAssignable(new Class[]{Integer.TYPE}, toClasses2, true)); // with autoboxing
        assertFalse(ClassUtils.isAssignable(new Class[]{Integer.TYPE}, toClasses2, false)); // without autoboxing

        // Test with null arrays
        assertTrue(ClassUtils.isAssignable((Class<?>[]) null, (Class<?>[]) null, false));
        assertFalse(ClassUtils.isAssignable(new Class[]{String.class}, (Class<?>[]) null, false));
        assertFalse(ClassUtils.isAssignable((Class<?>[]) null, new Class[]{String.class}, false));

        // Test with different array lengths
        assertFalse(ClassUtils.isAssignable(fromClasses, differentLength, false));
    }

    @Test
    public void testIsAssignableSingleClass() {
        // Test with null toClass
        assertFalse(ClassUtils.isAssignable(String.class, null));

        // Test with null cls
        assertTrue(ClassUtils.isAssignable(null, String.class));
        assertFalse(ClassUtils.isAssignable(null, int.class)); // null can't be assigned to primitive

        // Test with same class
        assertTrue(ClassUtils.isAssignable(String.class, String.class));

        // Test with assignable classes
        assertTrue(ClassUtils.isAssignable(String.class, Object.class));
        assertTrue(ClassUtils.isAssignable(String.class, CharSequence.class));

        // Test primitive assignments (with autoboxing)
        assertTrue(ClassUtils.isAssignable(int.class, Integer.class, true));
        assertTrue(ClassUtils.isAssignable(Integer.class, int.class, true));
        assertFalse(ClassUtils.isAssignable(int.class, Integer.class, false)); // without autoboxing

        // Test primitive widening
        assertTrue(ClassUtils.isAssignable(int.class, long.class, true));
        assertTrue(ClassUtils.isAssignable(int.class, float.class, true));
        assertTrue(ClassUtils.isAssignable(int.class, double.class, true));
        assertFalse(ClassUtils.isAssignable(boolean.class, int.class, true));
    }

    @Test
    public void testIsDerived() {
        // Test with null arguments
        assertFalse(ClassUtils.isDerived(null, Object.class));
        assertFalse(ClassUtils.isDerived(String.class));
        assertFalse(ClassUtils.isDerived(String.class, (Class[]) null));

        // Test with derived class
        assertTrue(ClassUtils.isDerived(String.class, Object.class, CharSequence.class));
        assertTrue(ClassUtils.isDerived(String.class, Object.class));
        assertFalse(ClassUtils.isDerived(Object.class, String.class));
    }

    @Test
    public void testGetAllInterfaces() {
        Set<Class<?>> interfaces = ClassUtils.getAllInterfaces(String.class);
        assertTrue(interfaces.contains(CharSequence.class));
        assertTrue(interfaces.contains(Comparable.class));
        assertTrue(interfaces.contains(Serializable.class));
        // Object is a class, not an interface, so it shouldn't be in getAllInterfaces
        assertFalse(interfaces.contains(Object.class));

        // Test with primitive type (should return empty set)
        assertEquals(0, ClassUtils.getAllInterfaces(int.class).size());

        // Test with null (should return empty set)
        assertEquals(0, ClassUtils.getAllInterfaces(null).size());

        // Test with interface filter
        Predicate<Class<?>> serializableFilter = clazz -> Serializable.class.isAssignableFrom(clazz);
        interfaces = ClassUtils.getAllInterfaces(String.class, serializableFilter);
        assertTrue(interfaces.contains(Serializable.class));
    }

    @Test
    public void testGetAllClasses() {
        Set<Class<?>> allClasses = ClassUtils.getAllClasses(String.class, false, clazz -> true);
        assertTrue(allClasses.contains(Object.class));
        assertFalse(allClasses.contains(String.class)); // excluded self since includedSelf is false

        // Test with includedSelf = true
        allClasses = ClassUtils.getAllClasses(String.class, true, clazz -> true);
        assertTrue(allClasses.contains(String.class));
        assertTrue(allClasses.contains(Object.class));

        // Test with primitive type (should return empty set)
        assertEquals(0, ClassUtils.getAllClasses(int.class, false, clazz -> true).size());

        // Test with null (should return empty set)
        assertEquals(0, ClassUtils.getAllClasses(null, false, clazz -> true).size());
    }

    @Test
    public void testGetAllSuperClasses() {
        Set<Class<?>> superClasses = ClassUtils.getAllSuperClasses(String.class);
        assertTrue(superClasses.contains(Object.class));

        // Should not include itself
        assertFalse(superClasses.contains(String.class));

        // Test with primitive type (should return empty set)
        assertEquals(0, ClassUtils.getAllSuperClasses(int.class).size());

        // Test with null (should return empty set)
        assertEquals(0, ClassUtils.getAllSuperClasses(null).size());
    }

    @Test
    public void testResolvePrimitiveClassName() {
        assertEquals(int.class, ClassUtils.resolvePrimitiveClassName("int"));
        assertEquals(boolean.class, ClassUtils.resolvePrimitiveClassName("boolean"));
        assertEquals(long.class, ClassUtils.resolvePrimitiveClassName("long"));
        assertEquals(double.class, ClassUtils.resolvePrimitiveClassName("double"));
        // Note: 'void' is not included in PRIMITIVE_TYPE_NAME_MAP, so it returns null
        assertNull(ClassUtils.resolvePrimitiveClassName("void"));

        // Note: resolvePrimitiveClassName does not handle "[]" notation, only internal JVM notation like "[I"
        // For "int[]" notation, you should use forName method instead
        assertNull(ClassUtils.resolvePrimitiveClassName("int[]"));
        assertNull(ClassUtils.resolvePrimitiveClassName("boolean[]"));

        // Test with invalid names
        assertNull(ClassUtils.resolvePrimitiveClassName("notAPrimitive"));
        assertNull(ClassUtils.resolvePrimitiveClassName(null));
        assertNull(ClassUtils.resolvePrimitiveClassName("java.lang.String"));
    }

    @Test
    public void testForName() throws ClassNotFoundException {
        // Test with primitive types
        assertEquals(int.class, ClassUtils.forName("int", null));
        assertEquals(String.class, ClassUtils.forName("java.lang.String", null));

        // Test with array types
        assertEquals(String[].class, ClassUtils.forName("java.lang.String[]", null));
        assertEquals(int[].class, ClassUtils.forName("int[]", null));

        // Test with invalid class name
        try {
            ClassUtils.forName("com.nonexistent.Class", null);
            fail("Expected ClassNotFoundException to be thrown");
        } catch (ClassNotFoundException e) {
            // Expected
        }
    }

    @Test
    public void testResolveClass() {
        // Test with valid class
        assertNotNull(ClassUtils.resolveClass("java.lang.String"));
        assertEquals(String.class, ClassUtils.resolveClass("java.lang.String"));

        // Test with invalid class (should return null)
        assertNull(ClassUtils.resolveClass("com.nonexistent.Class"));

        // Test with primitive
        assertEquals(int.class, ClassUtils.resolveClass("int"));
    }

    @Test
    public void testLoadClass() {
        // Test with valid class
        Class<?> clazz = ClassUtils.loadClass("java.lang.String");
        assertNotNull(clazz);
        assertEquals(String.class, clazz);

        // Test with invalid class (should throw exception)
        try {
            ClassUtils.loadClass("com.nonexistent.Class");
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            // Expected
        }
    }

    @Test
    public void testPrimitiveToWrapper() {
        assertEquals(Integer.class, ClassUtils.primitiveToWrapper(int.class));
        assertEquals(Boolean.class, ClassUtils.primitiveToWrapper(boolean.class));
        assertEquals(Long.class, ClassUtils.primitiveToWrapper(long.class));
        assertEquals(Double.class, ClassUtils.primitiveToWrapper(double.class));
        assertEquals(Void.TYPE, ClassUtils.primitiveToWrapper(void.class)); // Special case

        // Test with non-primitive class
        assertEquals(String.class, ClassUtils.primitiveToWrapper(String.class));

        // Test with null
        assertNull(ClassUtils.primitiveToWrapper(null));
    }

    @Test
    public void testWrapperToPrimitive() {
        assertEquals(int.class, ClassUtils.wrapperToPrimitive(Integer.class));
        assertEquals(boolean.class, ClassUtils.wrapperToPrimitive(Boolean.class));
        assertEquals(long.class, ClassUtils.wrapperToPrimitive(Long.class));
        assertEquals(double.class, ClassUtils.wrapperToPrimitive(Double.class));

        // Test with non-wrapper class
        assertNull(ClassUtils.wrapperToPrimitive(String.class));

        // Test with null
        assertNull(ClassUtils.wrapperToPrimitive(null));
    }

    @Test
    public void testGetClasses() {
        // Test with regular objects
        Class<?>[] classes = ClassUtils.getClasses("string", 123, 45.6);
        assertEquals(3, classes.length);
        assertEquals(String.class, classes[0]);
        assertEquals(Integer.class, classes[1]);
        assertEquals(Double.class, classes[2]);

        // Test with null values
        classes = ClassUtils.getClasses("string", null, 123);
        assertEquals(3, classes.length);
        assertEquals(String.class, classes[0]);
        assertEquals(Object.class, classes[1]); // null is mapped to Object.class
        assertEquals(Integer.class, classes[2]);

        // Test with NullWrapperBean
        ClassUtils.NullWrapperBean<String> nullWrapper = new ClassUtils.NullWrapperBean<>(String.class);
        classes = ClassUtils.getClasses("test", nullWrapper);
        assertEquals(String.class, classes[0]);
        assertEquals(String.class, classes[1]); // null wrapper specifies String type
    }

    @Test
    public void testGetDefaultValue() {
        assertEquals(0, ClassUtils.getDefaultValue(int.class));
        assertEquals(0L, ClassUtils.getDefaultValue(long.class));
        assertEquals((short) 0, ClassUtils.getDefaultValue(short.class));
        assertEquals((char) 0, ClassUtils.getDefaultValue(char.class));
        assertEquals((byte) 0, ClassUtils.getDefaultValue(byte.class));
        assertEquals(0D, ClassUtils.getDefaultValue(double.class));
        assertEquals(0f, ClassUtils.getDefaultValue(float.class));
        assertEquals(false, ClassUtils.getDefaultValue(boolean.class));

        // Non-primitive types should return null
        assertNull(ClassUtils.getDefaultValue(String.class));
        assertNull(ClassUtils.getDefaultValue(Object.class));
    }

    @Test
    public void testGetPrimitiveDefaultValue() {
        assertEquals(0, ClassUtils.getPrimitiveDefaultValue(int.class));
        assertEquals(0L, ClassUtils.getPrimitiveDefaultValue(long.class));
        assertEquals((short) 0, ClassUtils.getPrimitiveDefaultValue(short.class));
        assertEquals((char) 0, ClassUtils.getPrimitiveDefaultValue(char.class));
        assertEquals((byte) 0, ClassUtils.getPrimitiveDefaultValue(byte.class));
        assertEquals(0D, ClassUtils.getPrimitiveDefaultValue(double.class));
        assertEquals(0f, ClassUtils.getPrimitiveDefaultValue(float.class));
        assertEquals(false, ClassUtils.getPrimitiveDefaultValue(boolean.class));

        // Non-primitive types should return null
        assertNull(ClassUtils.getPrimitiveDefaultValue(String.class));
    }
}