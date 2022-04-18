package runner;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("tests")
@IncludeTags({"GoogleTest", "DatePickerTest"})
public class SuiteRunner {
}
