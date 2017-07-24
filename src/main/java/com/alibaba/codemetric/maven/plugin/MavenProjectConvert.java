package com.alibaba.codemetric.maven.plugin;

import com.alibaba.codemetric.maven.bootstrap.OutputConfig;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;
import com.alibaba.codemetric.maven.plugin.DependencyCollector.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fangqiao.jfq on 2017/7/7.
 */
public class MavenProjectConvert {
    private MavenProject project;
    private DependencyCollector collector;
    private List<Dependency> dependencies;
    private final ArtifactRepository localRepository;
    private Set<String> paths = new HashSet<>();
    //    private String path;
    private OutputConfig config;

    private static final String UNABLE_TO_DETERMINE_PROJECT_STRUCTURE_EXCEPTION_MESSAGE = "Unable to determine structure of project."
            + " Probably you use Maven Advanced Reactor Options with a broken tree of modules.";


    public MavenProjectConvert(MavenProject project, DependencyCollector collector,
                               ArtifactRepository localRepository) {
        this.project = project;
        this.collector = collector;
        this.localRepository = localRepository;
        config = new OutputConfig();
    }

//    public List<String> getDepenciesPaths() {
//        return depenciesPaths;
//    }

//    public String getPath() {
//        return path;
//    }

    private void setDepenciesPaths() {
        dependencies = collector.collectProjectDependencies(project);
//        depenciesPaths = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Dependency dependency : dependencies) {
            addPath(dependency);
        }
        for (String path : paths) {
            sb.append(localRepository.getBasedir())
                    .append("/")
                    .append(path)
                    .append(";");
        }
        //如果有依赖包
        if (sb.length() > 0)
            config.setDependencyPath(sb.substring(0, sb.length() - 1));
        else
            config.setDependencyPath("");
//        path = sb.substring(0, sb.length() - 1);
    }

    private void addPath(Dependency dependency) {
        Artifact artifact = dependency.getArtifact();
        if (artifact == null || "test".equals(artifact.getScope()))
            return;
        String path = localRepository.pathOf(artifact);
        if (paths.contains(path))
            return;
        paths.add(path);
        for (int i = 0; i < dependency.dependencies().size(); i++) {
            addPath(dependency.dependencies().get(i));
        }
    }

    //默认的源文件目录：baseDir/src/main/java
    private void setSourceFilePath() {
        //手工设定项目的源代码
        String sourcePath = project.getBuild().getSourceDirectory();
        if (sourcePath != null)
            config.setSourceFilePath(project.getBuild().getSourceDirectory());
        config.setSourceFilePath(project.getBasedir().getAbsolutePath() + "/src/main/java/");
    }

    /*
    默认的编译结果保存在baseDir/target/classes/
    目录下
     */
    private void setCompileFilePath() {
        // outputDirectory 指的是所有的Java文件编译后保存的地址,
        //在build标签中可以指明。
        if (project.getBuild().getOutputDirectory() == null)
            config.setCompileFilePath(project.getBasedir().getAbsolutePath() + "/target/classes/");
        config.setCompileFilePath(project.getBuild().getOutputDirectory());

    }

    /*
    默认编译后产生的jar包或者war包在target目录下，
    pom文件中不指定target编译地址。
     */
    private void setJarFilePath() {
        //directory 在build标签中指明，（没有指明则有一个默认地址,即为target/目录下）
        //目前getDirectory返回的结果就是输出路径，不会出现空值。
//        String directory = project.getBuild().getDirectory();
        StringBuilder sb = new StringBuilder();
        sb.append(project.getBuild().getDirectory())
                .append("/")
                .append(project.getArtifactId())
                .append("-")
                .append(project.getVersion())
                .append(".")
                .append(project.getModel().getPackaging());
//        if (directory == null) {
//            config.setOutputJarPath(project.getBasedir() + "/target/" + sb.toString());
//        }
        config.setOutputJarPath(sb.toString());
    }

    /*
     * 设置源代码文件的编码方式，maven项目中通过在pom文件中properties
     * 标签下添加project.build.sourceEncoding确定源代码的编码。
     * 如果properties中没有相关设置，默认设置为UTF-8编码
     * 否则根据项目具体设置进行。
     */
    private void setSourceFileEnconding() {
        String encoding = (String) project.getProperties().get("project.build.sourceEncoding");
        if (encoding == null) {
            config.setSourceFileEncoding("UTF-8");
        } else config.setSourceFileEncoding(encoding);

    }

    public void setConfig() {
        setDepenciesPaths();
        setJarFilePath();
        setCompileFilePath();
        setSourceFilePath();
        setSourceFileEnconding();
    }

    public OutputConfig getConfig() {
        return config;
    }


}
