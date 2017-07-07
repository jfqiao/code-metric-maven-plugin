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

@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {
	@Parameter(defaultValue = "${session}", readonly = true)
	private MavenSession session;

	@Parameter(defaultValue = "${localRepository}", required = true, readonly = true)
	private ArtifactRepository localRepository;

	@Component
	private DependencyTreeBuilder dependencyTreeBuilder;

	@Component
	private RuntimeInformation runtimeInformation;

	public void execute() throws MojoExecutionException {
		DependencyCollector dependencyCollector = new DependencyCollector(dependencyTreeBuilder, localRepository);
		List<MavenProject> mavenProjects = session.getProjects();

		for (MavenProject pro : mavenProjects) {
//
		}
//		getLog().info(dependencies.toString());
		Properties properties = session.getUserProperties();
		System.out.println(localRepository.toString());
	}

}
