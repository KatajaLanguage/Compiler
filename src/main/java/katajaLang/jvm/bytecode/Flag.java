package katajaLang.jvm.bytecode;

public interface Flag {
    int PUBLIC       = 0x0001;
    int PRIVATE      = 0x0002;
    int PROTECTED    = 0x0004;
    int STATIC       = 0x0008;
    int FINAL        = 0x0010;
    int SUPER        = 0x0020;
    int SYNCHRONIZED = 0x0020;
    int VOLATILE     = 0x0040;
    int BRIDGE       = 0x0040;
    int TRANSIENT    = 0x0080;
    int VARARGS      = 0x0080;
    int NATIVE       = 0x0100;
    int INTERFACE    = 0x0200;
    int ABSTRACT     = 0x0400;
    int STRICT       = 0x0800;
    int SYNTHETIC    = 0x1000;
    int ANNOTATION   = 0x2000;
    int ENUM         = 0x4000;
    int MODULE       = 0x8000;
}