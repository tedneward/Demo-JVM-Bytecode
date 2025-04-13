package com.newardassociates.demo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;

import javassist.*;
import javassist.bytecode.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test public void appIsFrozen() {
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cApp = pool.get("com.newardassociates.demo.App");
            assertEquals("com.newardassociates.demo.App", cApp.getName());

            assertEquals(false, cApp.isFrozen());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("We had a problem boss: " + ex.getMessage());
        }
    }

    @Test public void appGreetingCanBeModified() {
        System.out.println("Attempting to modify App");

        // Let's create a new CL to define our modified App.
        // But we must make sure this CL doesn't use our CL as a parent
        ByteArrayClassLoader bacl = new ByteArrayClassLoader(AppTest.class.getClassLoader().getParent());

        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cApp = pool.get("com.newardassociates.demo.App");
            assertEquals("com.newardassociates.demo.App", cApp.getName());

            CtMethod gg = cApp.getDeclaredMethod("getGreeting");
            assertEquals("getGreeting", gg.getName());
            gg.insertBefore("System.out.println(\"You called?\");");

            byte[] newAppBC = cApp.toBytecode();
            bacl.addClassData("com.newardassociates.demo.App", newAppBC);
            Class<?> newAppClass = Class.forName("com.newardassociates.demo.App", true, bacl);
            Object newAppInstance = newAppClass.getDeclaredConstructor().newInstance();
            Method getGreeting = newAppClass.getDeclaredMethod("getGreeting");
            String greeting = (String)getGreeting.invoke(newAppInstance);
            assertEquals("Hello World!", greeting);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("We had a problem boss: " + ex.getMessage());
        }
    }

    @Test public void synthesizeAPoint() {
        ClassPool pool = ClassPool.getDefault();
        try {
            ClassFile pointClass = new ClassFile(false, "com.newardassociates.demo.Point", "java.lang.Object");
            pointClass.setAccessFlags(AccessFlag.PUBLIC.mask());

            // public int x;
            FieldInfo f = new FieldInfo(pointClass.getConstPool(), "x", "I");
            f.setAccessFlags(AccessFlag.PUBLIC.mask());
            pointClass.addField(f);
            // public int y;
            f = new FieldInfo(pointClass.getConstPool(), "y", "I");
            f.setAccessFlags(AccessFlag.PUBLIC.mask());
            pointClass.addField(f);
            // default constructor
            Bytecode code = new Bytecode(pointClass.getConstPool());
            code.addAload(0);
            code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
            code.addReturn(null);
            code.setMaxLocals(1);
            MethodInfo minfo = new MethodInfo(pointClass.getConstPool(), MethodInfo.nameInit, "()V");
            minfo.setAccessFlags(AccessFlag.PUBLIC.mask());
            minfo.setCodeAttribute(code.toCodeAttribute());
            pointClass.addMethod(minfo);
            // public String toString() { return "(" + x + "," + y + ")"; }
            // This can actually get synthesized two ways; one is with invokedynamic:
            /*
                aload_0
                getfield      #7                  // Field x:I
                aload_0
                getfield      #13                 // Field y:I
                invokedynamic #16,  0             // InvokeDynamic #0:makeConcatWithConstants:(II)Ljava/lang/String;
                areturn
             */
            /*
            code = new Bytecode(pointClass.getConstPool());
            code.addAload(0);
            code.addGetfield​(Bytecode.THIS, "x", "I");
            code.addAload(0);
            code.addGetfield​(Bytecode.THIS, "y", "I");
            code.addInvokedynamic​(0, "makeConcatWithConstants", "(II)Ljava/lang/String;");
            code.addReturn(Bytecode.THIS);
            code.setMaxLocals(2);
            minfo = new MethodInfo(pointClass.getConstPool(), "toString", "()Ljava/lang/String;");
            minfo.setCodeAttribute(code.toCodeAttribute());
            pointClass.addMethod(minfo);

            // But... this requires us to figure out how to create a BootstrapMethodsAttribute
            // so the invokedynamic can use that in its call (the first param is the index to the
            // method in the BSMA table).
             */
            // So we can also do the concatenation by hand, using StringBuffer:
            /*
                new           #7                  // class java/lang/StringBuffer
                dup
                invokespecial #9                  // Method java/lang/StringBuffer."<init>":()V
                astore_1
                aload_1
                ldc           #10                 // String (
                invokevirtual #12                 // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
                pop
                aload_1
                aload_0
                getfield      #16                 // Field x:I
                invokevirtual #22                 // Method java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
                pop
                aload_1
                ldc           #25                 // String ,
                invokevirtual #12                 // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
                pop
                aload_1
                aload_0
                getfield      #27                 // Field y:I
                invokevirtual #22                 // Method java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
                pop
                aload_1
                ldc           #30                 // String )
                invokevirtual #12                 // Method java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
                pop
                aload_1
                invokevirtual #32                 // Method java/lang/StringBuffer.toString:()Ljava/lang/String;
                areturn
             */
            code = new Bytecode(pointClass.getConstPool());
            code.addNew("java/lang/StringBuffer");
            code.add(Bytecode.DUP);
            code.addInvokespecial("java/lang/StringBuffer", MethodInfo.nameInit, "()V");
            code.addAstore(1);
            code.addAload(1);
            code.addLdc("(");
            code.addInvokevirtual("java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
            code.add(Bytecode.POP);
            code.addAload(1);
            code.addAload(0);
            code.addGetfield​(Bytecode.THIS, "x", "I");
            code.addInvokevirtual("java/lang/StringBuffer", "append", "(I)Ljava/lang/StringBuffer;");
            code.add(Bytecode.POP);
            code.addAload(1);
            code.addLdc(",");
            code.addInvokevirtual("java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
            code.add(Bytecode.POP);
            code.addAload(1);
            code.addAload(0);
            code.addGetfield​(Bytecode.THIS, "y", "I");
            code.addInvokevirtual("java/lang/StringBuffer", "append", "(I)Ljava/lang/StringBuffer;");
            code.add(Bytecode.POP);
            code.addAload(1);
            code.addLdc(")");
            code.addInvokevirtual("java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
            code.add(Bytecode.POP);
            code.addAload(1);
            code.addInvokevirtual("java/lang/StringBuffer", "toString", "()Ljava/lang/String;");
            code.addReturn(pool.get("java.lang.String"));
            code.setMaxLocals(2);
            code.setMaxStack(2);
            minfo = new MethodInfo(pointClass.getConstPool(), "toString", "()Ljava/lang/String;");
            minfo.setCodeAttribute(code.toCodeAttribute());
            pointClass.addMethod(minfo);

            // Theoretically using Javassist we should be able to do this:
            //CtClass point = pool.makeClass(pointClass);
            //Class<?> clsPoint = point.toClass();

            // But that runs afoul of Java modules, so let's do this the hard way:
            // Let's create a new CL to define our modified App.
            // But we must make sure this CL doesn't use our CL as a parent
            ByteArrayClassLoader bacl = new ByteArrayClassLoader(AppTest.class.getClassLoader().getParent());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            pointClass.write(new DataOutputStream(baos));
            bacl.addClassData(pointClass.getName(), baos.toByteArray());
            Class<?> clsPoint = Class.forName("com.newardassociates.demo.Point", true, bacl);

            Object objPoint = clsPoint.getDeclaredConstructor().newInstance();
            assertEquals("(0,0)", objPoint.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail("We had a problem boss--check the build report");
        }
    }
}
