package fr.adrienbrault.idea.symfony2plugin.tests.stubs.indexes;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndexImpl;
import fr.adrienbrault.idea.symfony2plugin.routing.dict.RouteInterface;
import fr.adrienbrault.idea.symfony2plugin.stubs.indexes.AnnotationRoutesStubIndex;
import fr.adrienbrault.idea.symfony2plugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see fr.adrienbrault.idea.symfony2plugin.stubs.indexes.AnnotationRoutesStubIndex
 */
public class AnnotationRoutesStubIndexTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();

        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("AnnotationRoutesStubIndex.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testIndexOfMethodPatternAndClassPrefix() {
        assertIndexContains(AnnotationRoutesStubIndex.KEY, "blog_home");
        assertEquals("blog_home", getFirstValue("blog_home").getName());
        assertEquals("/foo/edit/{id}", getFirstValue("blog_home").getPath());
    }

    public void testThatEmptyRouteNameUseBundleMethodName() {
        assertIndexContains(AnnotationRoutesStubIndex.KEY, "my_foo_bar_car_index");
        assertEquals("my_foo_bar_car_index", getFirstValue("my_foo_bar_car_index").getName());
        assertEquals("/foo_bar/edit/{id}", getFirstValue("my_foo_bar_car_index").getPath());
    }

    private RouteInterface getFirstValue(@NotNull String key) {
        return FileBasedIndexImpl.getInstance().getValues(AnnotationRoutesStubIndex.KEY, key, GlobalSearchScope.allScope(getProject())).get(0);
    }
}
