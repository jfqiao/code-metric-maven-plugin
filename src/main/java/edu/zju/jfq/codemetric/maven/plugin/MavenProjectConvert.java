package edu.zju.jfq.codemetric.maven.plugin;

import org.apache.maven.project.MavenProject;
import edu.zju.jfq.codemetric.maven.plugin.DependencyCollector.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangqiao.jfq on 2017/7/7.
 */
public class MavenProjectConvert {
    private MavenProject project;
    private DependencyCollector collector;
    private List<Dependency> dependencies;
    private List<String> depenciesPaths;

    public MavenProjectConvert(MavenProject project, DependencyCollector collector) {
        this.project = project;
        this.collector = collector;
    }

    public List<String> getDepenciesPaths() {
        return depenciesPaths;
    }

    public void setDepenciesPaths() {
        dependencies = collector.collectProjectDependencies(project);
        depenciesPaths = new ArrayList<>();
        for(Dependency dependency : dependencies) {
            StringBuilder sb = new StringBuilder();
            sb.append(dependency.key().replaceAll(".", "\\"));
            sb.append("\\")
                    .append(dependency.version())
                    .append("\\")
                    .append()
        }
    }
}
