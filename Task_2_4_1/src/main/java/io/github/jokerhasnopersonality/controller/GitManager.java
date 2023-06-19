package io.github.jokerhasnopersonality.controller;

import java.io.File;
import org.eclipse.jgit.api.Git;

/**
 * Class for working with GitHub repositories.
 */
public class GitManager {
    /**
     * Downloads a repo of a student.

     * @param repo URI of a student's GitHub repository
     * @param studentId student's ID for creating a pathname
     * @param labsPath labs path to store a student's repo
     */
    public static void getRepo(String repo, String studentId, String labsPath) {
        // clone a repository from GitHub
        try {
            Git git = Git.cloneRepository()
                    .setURI(repo)
                    .setDirectory(new File(labsPath
                            + studentId))
                    .call();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("Cannot download repo from github: %s", repo));
        }
    }
}
