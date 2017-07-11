package edu.zju.jfq.codemetric.maven.plugin;

/**
 * Created by fangqiao.jfq on 2017/7/11.
 */

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.MojoRule;
import org.assertj.core.data.MapEntry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.skyscreamer.jsonassert.JSONAssert;

public class CodeMetricsMojoTest {
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Rule
//    public MojoRule mojoRule = new MojoRule();
//
//    private Log mockedLogger;
//
//    private CodeMetricsMojo getMojo(File baseDir) throws Exception {
////        CodeMetricsMojo.readyProjectsCounter.getAndSet(0);
//        return (CodeMetricsMojo) mojoRule.lookupConfiguredMojo(baseDir, "calculate  ");
//    }
//
//    @Before
//    public void setUpMocks() {
//        mockedLogger = mock(Log.class);
//    }
//
//    @Test
//    public void executeMojo() throws Exception {
//        executeProject("sample-project");
//
//        // passed in the properties of the profile and project
//        assertGlobalPropsContains(entry("sonar.host.url1", "http://myserver:9000"));
//        assertGlobalPropsContains(entry("sonar.host.url2", "http://myserver:9000"));
//    }
//
//    @Test
//    public void should_skip() throws Exception {
//        File propsFile = new File("target/dump.properties");
//        propsFile.delete();
//        executeProject("sample-project", "sonar.scanner.skip", "true");
//        assertThat(propsFile).doesNotExist();
//    }
//
//    @Test
//    public void shouldExportBinaries() throws Exception {
//        File baseDir = executeProject("sample-project");
//
//        assertPropsContains(entry("sonar.binaries", new File(baseDir, "target/classes").getAbsolutePath()));
//    }
//
//    @Test
//    public void shouldExportDefaultWarWebSource() throws Exception {
//        File baseDir = executeProject("sample-war-project");
//        assertPropsContains(entry("sonar.sources",
//                new File(baseDir, "src/main/webapp").getAbsolutePath() + ","
//                        + new File(baseDir, "pom.xml").getAbsolutePath() + ","
//                        + new File(baseDir, "src/main/java").getAbsolutePath()));
//    }
//
//    @Test
//    public void shouldExportOverridenWarWebSource() throws Exception {
//        File baseDir = executeProject("war-project-override-web-dir");
//        assertPropsContains(entry("sonar.sources",
//                new File(baseDir, "web").getAbsolutePath() + ","
//                        + new File(baseDir, "pom.xml").getAbsolutePath() + ","
//                        + new File(baseDir, "src/main/java").getAbsolutePath()));
//    }
//
//    @Test
//    public void shouldExportDependencies() throws Exception {
//        File baseDir = executeProject("export-dependencies");
//
//        Properties outProps = readProps("target/dump.properties");
//        String libJson = outProps.getProperty("sonar.maven.projectDependencies");
//
//        JSONAssert.assertEquals("[{\"k\":\"commons-io:commons-io\",\"v\":\"2.4\",\"s\":\"compile\",\"d\":["
//                + "{\"k\":\"commons-lang:commons-lang\",\"v\":\"2.6\",\"s\":\"compile\",\"d\":[]}" + "]},"
//                + "{\"k\":\"junit:junit\",\"v\":\"3.8.1\",\"s\":\"test\",\"d\":[]}]", libJson, true);
//
//        assertThat(outProps.getProperty("sonar.java.binaries")).isEqualTo(new File(baseDir, "target/classes").getAbsolutePath());
//        assertThat(outProps.getProperty("sonar.java.test.binaries")).isEqualTo(new File(baseDir, "target/test-classes").getAbsolutePath());
//    }
//
//    // MSONAR-135
//    @Test
//    public void shouldExportDependenciesWithSystemScopeTransitive() throws Exception {
//        executeProject("system-scope");
//
//        Properties outProps = readProps("target/dump.properties");
//        String libJson = outProps.getProperty("sonar.maven.projectDependencies");
//
//        JSONAssert.assertEquals(
//                "[{\"k\":\"org.codehaus.xfire:xfire-core\",\"v\":\"1.2.6\",\"s\":\"compile\",\"d\":[{\"k\":\"javax.activation:activation\",\"v\":\"1.1.1\",\"s\":\"system\",\"d\":[]}]}]",
//                libJson, true);
//    }
//
//    // MSONAR-113
//    @Test
//    public void shouldExportSurefireReportsPath() throws Exception {
//
//        File baseDir = executeProject("sample-project-with-surefire");
//        assertPropsContains(entry("sonar.junit.reportsPath", new File(baseDir, "target/surefire-reports").getAbsolutePath()));
//    }
//
//    // MSONAR-113
//    @Test
//    public void shouldExportSurefireCustomReportsPath() throws Exception {
//        File baseDir = executeProject("sample-project-with-custom-surefire-path");
//        assertPropsContains(entry("sonar.junit.reportsPath", new File(baseDir, "target/tests").getAbsolutePath()));
//    }
//
//    @Test
//    public void reuse_findbugs_exclusions_from_reporting() throws IOException, Exception {
//        File baseDir = executeProject("project-with-findbugs-reporting");
//        assertPropsContains(entry("sonar.findbugs.excludeFilters", new File(baseDir, "findbugs-exclude.xml").getAbsolutePath()));
//    }
//
//    @Test
//    public void reuse_findbugs_exclusions_from_plugin() throws IOException, Exception {
//        File baseDir = executeProject("project-with-findbugs-build");
//        assertPropsContains(entry("sonar.findbugs.excludeFilters", new File(baseDir, "findbugs-exclude.xml").getAbsolutePath()));
//    }
//
//    @Test
//    public void reuse_findbugs_exclusions_from_plugin_management() throws IOException, Exception {
//        File baseDir = executeProject("project-with-findbugs-plugin-management");
//        assertPropsContains(entry("sonar.findbugs.excludeFilters", new File(baseDir, "findbugs-exclude.xml").getAbsolutePath()));
//    }
//
//    @Test
//    public void should_get_java_target_source() throws IOException, Exception {
//        executeProject("sample-project-with-java-version");
//        assertPropsContains(entry("sonar.java.target", "1.8"));
//        assertPropsContains(entry("sonar.java.source", "1.7"));
//    }
//
//    @Test
//    public void verbose() throws Exception {
//        when(mockedLogger.isDebugEnabled()).thenReturn(true);
//        executeProject("project-with-findbugs-reporting");
//        verify(mockedLogger, atLeastOnce()).isDebugEnabled();
//        assertThat(readProps("target/dump.properties.global")).contains((entry("sonar.verbose", "true")));
//    }
//
//    private File executeProject(String projectName, String... properties) throws Exception {
//        ArtifactRepository artifactRepo = new DefaultArtifactRepository("local", this.getClass().getResource("SonarQubeMojoTest/repository").toString(), new DefaultRepositoryLayout());
//
//        File baseDir = new File("src/test/resources/org/sonarsource/scanner/maven/SonarQubeMojoTest/" + projectName);
//        CodeMetricsMojo mojo = getMojo(baseDir);
//        mojo.getSession().getProjects().get(0).setExecutionRoot(true);
//
//        mojo.setLocalRepository(artifactRepo);
//        mojo.setLog(mockedLogger);
//
//        Properties userProperties = mojo.getSession().getUserProperties();
//        if ((properties.length % 2) != 0) {
//            throw new IllegalArgumentException("invalid number properties");
//        }
//
//        for (int i = 0; i < properties.length; i += 2) {
//            userProperties.put(properties[i], properties[i + 1]);
//        }
//
//        mojo.execute();
//
//        return baseDir;
//    }
//
//    @SafeVarargs
//    private final void assertPropsContains(MapEntry<String, String>... entries) throws FileNotFoundException, IOException {
//        assertThat(readProps("target/dump.properties")).contains(entries);
//    }
//
//    private void assertGlobalPropsContains(MapEntry<String, String> entries) throws FileNotFoundException, IOException {
//        assertThat(readProps("target/dump.properties.global")).contains(entries);
//    }
//
//    private Properties readProps(String filePath) throws FileNotFoundException, IOException {
//        FileInputStream fis = null;
//        try {
//            File dump = new File(filePath);
//            Properties props = new Properties();
//            fis = new FileInputStream(dump);
//            props.load(fis);
//            return props;
//        } finally {
//            IOUtils.closeQuietly(fis);
//        }
//    }
}
