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
import org.eclipse.sisu.Parameters;;import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

	public void execute() throws MojoExecutionException {
	    try {
            DependencyCollector dependencyCollector = new DependencyCollector(dependencyTreeBuilder, localRepository);
            List<MavenProject> mavenProjects = session.getProjects();

            MavenProjectConvert mpc = null;
            for (MavenProject pro : mavenProjects) {
                if (pro.getPackaging().equals("pom") || pro.getCollectedProjects().size() > 0)
                    continue;
                mpc = new MavenProjectConvert(pro, dependencyCollector, localRepository);
                mpc.setConfig();
                getLog().error(mpc.getConfig().toString());
                //以下调用分析代码即可。
            }
        } catch (Exception e) {
	        getLog().error("Some error in your project.");
        }
	}

}
