package edu.zju.jfq.codemetric.maven.bootstrap;

/**
 * Created by fangqiao.jfq on 2017/7/10.
 */
public class OutputConfig {
    //依赖包路径，以";"分隔
    private String dependencyPath;
    //源代码编译jar包或war包地址，
    private String outputJarPath;
    //源代码编译后字节码地址
    private String compileFilePath;
    //源代码地址
    private String sourceFilePath;

    public String getdependencyPath() {
        return dependencyPath;
    }

    public void setDependencyPath(String dependencyPath) {
        this.dependencyPath = dependencyPath;
    }

    public String getOutputJarPath() {
        return outputJarPath;
    }

    public void setOutputJarPath(String outputJarPath) {
        this.outputJarPath = outputJarPath;
    }

    public String getCompileFilePath() {
        return compileFilePath;
    }

    public void setCompileFilePath(String compileFilePath) {
        this.compileFilePath = compileFilePath;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }
    @Override
    public String toString() {
        return "Source Path: " + sourceFilePath + "\r\n"
                + "Compile Path: " + compileFilePath + "\r\n"
                + "Package Path: " + outputJarPath + "\r\n"
                + "Dependency Path : " + dependencyPath;
    }
}
