package uwu.nekorise;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.glavo.rcon.AuthenticationException;
import org.glavo.rcon.Rcon;

import java.io.IOException;
import java.util.List;

@Mojo(
        name = "rcon",
        defaultPhase = LifecyclePhase.PACKAGE,
        threadSafe = true
)
public class RconMojo extends AbstractMojo {
    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    @Parameter(property = "host", required = true)
    private String host;
    @Parameter(property = "port", required = true)
    private String port;
    @Parameter(property = "password", required = true)
    private String password;
    @Parameter(property = "commands", required = true)
    private List<String> commands;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("RCON goal skipped");
            return;
        }

        try {
            getLog().info("Connecting to " + host + ":" + port + "...");
            Rcon rcon = new Rcon(host, Integer.parseInt(port), password);
            getLog().info("Connected!");

            for (String command : commands) {
                getLog().info(" ");
                getLog().info("Executing command \"" + command + "\"...");
                String response = rcon.command(command);
                getLog().info("Response: " + response);
                getLog().info(" ");
            }
        } catch (IOException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
