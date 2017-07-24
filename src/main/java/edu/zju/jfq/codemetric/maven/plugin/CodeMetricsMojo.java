package edu.zju.jfq.codemetric.maven.plugin;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;

import java.util.List;

import ccnt.analysis.metrics.Main;
import org.eclipse.core.runtime.CoreException;

@Mojo(name = "calculate")
public class CodeMetricsMojo extends AbstractMojo {
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${localRepository}", required = true, readonly = true)
    private ArtifactRepository localRepository;

    @Component
    private DependencyTreeBuilder dependencyTreeBuilder;

    @Component
    private RuntimeInformation runtimeInformation;

    public MavenSession getSession() {
        return session;
    }

    public void setLocalRepository(ArtifactRepository localRepository) {
        this.localRepository = localRepository;
    }

    public void execute() throws MojoExecutionException {
        try {
            DependencyCollector dependencyCollector = new DependencyCollector(dependencyTreeBuilder, localRepository);
//            List<MavenProject> mavenProjects = session.getProjects();
            MavenProject pro = session.getCurrentProject();
//            for (MavenProject pro : mavenProjects) {
            if (pro.getPackaging().equals("pom") || pro.getCollectedProjects().size() > 0)
                return;
            MavenProjectConvert mpc = new MavenProjectConvert(pro, dependencyCollector, localRepository);
            mpc.setConfig();
//                getLog().error(mpc.getConfig().toString());
            Main.calculate(mpc.getConfig().getSourceFilePath(),
                    mpc.getConfig().getCompileFilePath(),
                    mpc.getConfig().getOutputJarPath(),
                    mpc.getConfig().getSourceFileEncoding(),
                    mpc.getConfig().getdependencyPath(),
                    pro.getArtifactId(),
                    null,
                    pro.getBasedir().getAbsolutePath());
//            }
        } catch (Exception e) {
            getLog().error("Some error in pom.xml file.");
            throw new MojoExecutionException("Code Metric maven plugin failed.");
        }
    }

}
