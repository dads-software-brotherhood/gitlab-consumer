package mx.dads.infotec.core.gitlab.consumer.service;

/**
 * Public service contants.
 * 
 * @author erik.valdivieso
 */
public final class ServiceConstants {
    /**
     * Groups api path.
     */
    public static final String GROUPS = "/groups";
    /**
     * Projects' groups path.
     */
    public static final String GROUP_PROJECTS = "/groups/{0,number}/projects";
    /**
     * Commits' project path.
     */
    public static final String PROJECT_COMMITS = "/projects/{0,number}/repository/commits";
    /**
     * Single project commit path.
     */
    public static final String PROJECT_SINGLE_COMMIT = "/projects/{0,number}/repository/commits/{1}";

    /**
     * Private because is a tool class.
     */
    private ServiceConstants() {
        // Do noting
    }

}