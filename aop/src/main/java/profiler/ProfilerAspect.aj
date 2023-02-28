package profiler;

public aspect ProfilerAspect {
    pointcut anyFunction(): call(* applications.*.*());

    before(): anyFunction() {
        Profiler.start(String.format("%s.%s",
                thisJoinPointStaticPart.getSignature().getDeclaringTypeName(),
                thisJoinPointStaticPart.getSignature().getName()
        ));
    }

    after(): anyFunction() {
        Profiler.end(String.format("%s.%s",
                thisJoinPointStaticPart.getSignature().getDeclaringTypeName(),
                thisJoinPointStaticPart.getSignature().getName()
        ));
    }

}
