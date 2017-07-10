/*
 * SonarQube Scanner for Maven
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.zju.jfq.codemetric.maven.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.apache.maven.shared.dependency.tree.filter.AncestorOrSelfDependencyNodeFilter;
import org.apache.maven.shared.dependency.tree.filter.DependencyNodeFilter;
import org.apache.maven.shared.dependency.tree.filter.StateDependencyNodeFilter;
import org.apache.maven.shared.dependency.tree.traversal.BuildingDependencyNodeVisitor;
import org.apache.maven.shared.dependency.tree.traversal.CollectingDependencyNodeVisitor;
import org.apache.maven.shared.dependency.tree.traversal.DependencyNodeVisitor;
import org.apache.maven.shared.dependency.tree.traversal.FilteringDependencyNodeVisitor;

import java.util.*;

public class DependencyCollector {

    private final DependencyTreeBuilder dependencyTreeBuilder;

    private final ArtifactRepository localRepository;

    public DependencyCollector(DependencyTreeBuilder dependencyTreeBuilder, ArtifactRepository localRepository) {
        this.dependencyTreeBuilder = dependencyTreeBuilder;
        this.localRepository = localRepository;
    }

    static class Dependency {

//    private final String groupId;

//    private final String artifactId;

//    private final String key;

//    private final String version;

        //    private String scope;
        private Artifact artifact;

        List<Dependency> dependencies = new ArrayList<Dependency>();

        public Dependency(String groupId, String artifactId, String version) {
//      this.key = key;
//      this.groupId = groupId;
//      this.artifactId = artifactId;
//      this.version = version;
        }

        public Dependency(Artifact artifact) {
//        this.scope = scope;
            this.artifact = artifact;
        }

//    public String key() {
//      return key;
//    }

//    public String getGroupId() {
//      return groupId;
//    }
//
//    public String getArtifactId() {
//      return artifactId;
//    }
//
//    public String getVersion() {
//      return version;
//    }

//    public String scope() {
//      return scope;
//    }
//
//    public Dependency setScope(String scope) {
//      this.scope = scope;
//      return this;
//    }

        public Artifact getArtifact() {
            return artifact;
        }

        public List<Dependency> dependencies() {
            return dependencies;
        }
    }

    public List<Dependency> collectProjectDependencies(MavenProject project) {
        final List<Dependency> result = new ArrayList<>();
        try {
            DependencyNode root = dependencyTreeBuilder.buildDependencyTree(project, localRepository, null);

            DependencyNodeVisitor visitor = new BuildingDependencyNodeVisitor(new DependencyNodeVisitor() {

                private Deque<Dependency> stack = new ArrayDeque<Dependency>();

                @Override
                public boolean visit(DependencyNode node) {
                    if (node.getParent() != null && node.getParent() != node) {
                        Dependency dependency = toDependency(node);
                        if (stack.isEmpty()) {
                            result.add(dependency);
                        } else {
                            stack.peek().dependencies().add(dependency);
                        }
                        stack.push(dependency);
                    }
                    return true;
                }

                @Override
                public boolean endVisit(DependencyNode node) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    return true;
                }
            });

            /// mode verbose OFF : do not show the same lib many times
            DependencyNodeFilter filter = StateDependencyNodeFilter.INCLUDED;

            CollectingDependencyNodeVisitor collectingVisitor = new CollectingDependencyNodeVisitor();
            DependencyNodeVisitor firstPassVisitor = new FilteringDependencyNodeVisitor(collectingVisitor, filter);
            root.accept(firstPassVisitor);

            DependencyNodeFilter secondPassFilter = new AncestorOrSelfDependencyNodeFilter(collectingVisitor.getNodes());
            visitor = new FilteringDependencyNodeVisitor(visitor, secondPassFilter);

            root.accept(visitor);

        } catch (DependencyTreeBuilderException e) {
            throw new IllegalStateException("Can not load the graph of dependencies of the project " + project, e);
        }
        return result;
    }

    private Dependency toDependency(DependencyNode node) {
//        String path = localRepository.pathOf(node.getArtifact());

//    String key = String.format("%s:%s", node.getArtifact().getGroupId(), node.getArtifact().getArtifactId());
        String version = node.getArtifact().getBaseVersion();
//    return new Dependency(node.getArtifact().getGroupId(),
//            node.getArtifact().getArtifactId(), version).setScope(node.getArtifact().getScope());
        return new Dependency(node.getArtifact());
    }

//  public String toJson(MavenProject project) {
//    return dependenciesToJson(collectProjectDependencies(project));
//  }
//
//  private String dependenciesToJson(List<Dependency> deps) {
//    StringBuilder json = new StringBuilder();
//    json.append('[');
//    serializeDeps(json, deps);
//    json.append(']');
//    return json.toString();
//  }
//
//  private void serializeDeps(StringBuilder json, List<Dependency> deps) {
//    for (Iterator<Dependency> dependencyIt = deps.iterator(); dependencyIt.hasNext();) {
//      serializeDep(json, dependencyIt.next());
//      if (dependencyIt.hasNext()) {
//        json.append(',');
//      }
//    }
//  }
//
//  private void serializeDep(StringBuilder json, Dependency dependency) {
//    json.append("{");
//    json.append("\"k\":\"");
//    json.append(dependency.key());
//    json.append("\",\"v\":\"");
//    json.append(dependency.version());
//    json.append("\",\"s\":\"");
//    json.append(dependency.scope());
//    json.append("\",\"d\":[");
//    serializeDeps(json, dependency.dependencies());
//    json.append("]");
//    json.append("}");
//  }
}
