package edu.zju.jfq.codemetric.maven.plugin;

import edu.zju.jfq.codemetric.maven.bootstrap.OutputConfig;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;
import edu.zju.jfq.codemetric.maven.plugin.DependencyCollector.*;

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
        config.setSourceFilePath(project.getBuild().getSourceDirectory());
//        config.setSourceFilePath(project.getCompileSourceRoots().get(0));
    }

    /*
    默认的编译结果保存在baseDir/target/classes/
    目录下
     */
    private void setCompileFilePath() {
        if (project.getBuild() == null)
            config.setCompileFilePath(project.getBasedir().getAbsolutePath() + "/target/classes/");
        config.setCompileFilePath(project.getBuild().getOutputDirectory());

    }

    /*
    默认编译后产生的jar包或者war包在target目录下，
    pom文件中不指定target编译地址。
     */
    private void setJarFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(project.getBuild().getOutputDirectory())
                .append("/")
                .append(project.getArtifactId())
                .append("-")
                .append(project.getVersion())
                .append(".")
                .append(project.getModel().getPackaging());
        config.setOutputJarPath(sb.toString());
    }

    public void setConfig() {
        setDepenciesPaths();
        setJarFilePath();
        setCompileFilePath();
        setSourceFilePath();
    }

    public OutputConfig getConfig() {
        return config;
    }
}
