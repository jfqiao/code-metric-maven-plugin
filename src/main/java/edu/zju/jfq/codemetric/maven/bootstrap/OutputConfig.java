package edu.zju.jfq.codemetric.maven.bootstrap;

/**
 * Created by fangqiao.jfq on 2017/7/10.
 */
public class OutputConfig {
    //依赖包路径，以";"分隔
    private String dependencyPath;
    //源代码编译jar包或war包地址， jar包地址可以在pom中指定，不指定就是默认地址。
    private String outputJarPath;
    //源代码编译后字节码地址, 编译后的字节码也不一定在target/classes文件夹下
    private String compileFilePath;
    //源代码地址, 注意在pom.xml模型中，源代码文件不一定都在src/main/java文件夹下，
    private String sourceFilePath;
    //源码编码方式，如果源码有多个不同文件夹，则对应中编码方式，与源码地址一一对应
    private String sourceFileEncoding;
    //过滤不分析文件的正则表达式
    private String[] filters;
    //对分析结果的输出目录
    private String outputDir;



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

    public String getSourceFileEncoding() {
        return sourceFileEncoding;
    }

    public void setSourceFileEncoding(String sourceFileEncoding) {
        this.sourceFileEncoding = sourceFileEncoding;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public String[] getFilters() {
        return filters;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    @Override
    public String toString() {
        return "Source Path: " + sourceFilePath + "\r\n"
                + "Source File Path Encoding: " + sourceFileEncoding + "\r\n"
                + "Compile Path: " + compileFilePath + "\r\n"
                + "Package Path: " + outputJarPath + "\r\n"
                + "Dependency Path : " + dependencyPath;
    }
}
