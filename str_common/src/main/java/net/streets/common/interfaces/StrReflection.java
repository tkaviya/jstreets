package net.streets.common.interfaces;

import java.util.Optional;

public interface StrReflection {

    int DEPTH_CURRENT_METHOD = 3;

    int DEPTH_CALLER_METHOD = 4;

    default Optional<String> getMethodName(int depth) {
        if (Thread.currentThread().getStackTrace().length > depth) {
            return Optional.ofNullable(Thread.currentThread().getStackTrace()[depth].getMethodName());
        } else {
            return Optional.empty();
        }
    }

    default Optional<String> getClassName(int depth) {
        if (Thread.currentThread().getStackTrace().length > depth) {
            return Optional.ofNullable(Thread.currentThread().getStackTrace()[depth].getClassName());
        } else {
            return Optional.empty();
        }
    }

    default Optional<String> getFileName(int depth) {
        if (Thread.currentThread().getStackTrace().length > depth) {
            return Optional.ofNullable(Thread.currentThread().getStackTrace()[depth].getFileName());
        } else {
            return Optional.empty();
        }
    }

    default Optional<Integer> getLineNumber(int depth) {
        if (Thread.currentThread().getStackTrace().length > depth) {
            return Optional.ofNullable(Thread.currentThread().getStackTrace()[depth].getLineNumber());
        } else {
            return Optional.empty();
        }
    }

    default Optional<String> currentMethodName() {
        return getMethodName(DEPTH_CURRENT_METHOD);
    }

    default Optional<String> callerMethodName() {
        return getMethodName(DEPTH_CALLER_METHOD);
    }

    default Optional<String> currentClassFullName() {
        return getClassName(DEPTH_CURRENT_METHOD);
    }

    default Optional<String> callerClassFullName() {
        return getClassName(DEPTH_CALLER_METHOD);
    }

    default Optional<String> currentClassName() {
        return getClassName(DEPTH_CURRENT_METHOD).map((n) -> n.substring(n.lastIndexOf(".") + 1));
    }

    default Optional<String> callerClassName() {
        return getClassName(DEPTH_CALLER_METHOD).map((n) -> n.substring(n.lastIndexOf(".") + 1));
    }

    default Optional<String> currentFileName() {
        return getFileName(DEPTH_CURRENT_METHOD);
    }

    default Optional<String> callerFileName() {
        return getFileName(DEPTH_CALLER_METHOD);
    }

    default Optional<Integer> currentLineNumber() {
        return getLineNumber(DEPTH_CURRENT_METHOD);
    }

    default Optional<Integer> callerLineNumber() {
        return getLineNumber(DEPTH_CALLER_METHOD);
    }

//    default StrReflection getInstance() {
//        return new StrReflection() {
//        };
//    }
}
