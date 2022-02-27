package runner;

import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;
import utils.TestExecutionListener3;
import utils.TestExecutionListener4;
import utils.TestExecutionListener5;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

@Suite
@SuiteDisplayName("All existing test suite")
@SelectPackages("tests")
//@ExtendWith(TestExecutionListener1.class)

public class SuiteRunner {
}
