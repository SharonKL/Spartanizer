package il.org.spartan.refactoring.wring;

import static il.org.spartan.refactoring.utils.Funcs.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.refactoring.preferences.PluginPreferencesResources.*;
import il.org.spartan.refactoring.utils.*;

/** /** A {@link Wring} to convert
 *
 * <pre>
 * if (x)
 *   return b;
 * else {
 * }
 * </pre>
 *
 * into
 *
 * <pre>
 * if (x)
 *   return b;
 * </pre>
 *
 * @author Yossi Gil
 * @since 2015-08-01 */
public final class IfDegenerateElse extends Wring.ReplaceCurrentNode<IfStatement> {
  static boolean degenerateElse(final IfStatement s) {
    return elze(s) != null && Is.vacuousElse(s);
  }
  @Override String description(@SuppressWarnings("unused") final IfStatement __) {
    return "Remove vacuous 'else' branch";
  }
  @Override Statement replacement(final IfStatement s) {
    final IfStatement $ = duplicate(s);
    $.setElseStatement(null);
    return !Is.blockRequiredInReplacement(s, $) ? $ : subject.statement($).toBlock();
  }
  @Override boolean scopeIncludes(final IfStatement s) {
    return s != null && then(s) != null && degenerateElse(s);
  }
  @Override WringGroup wringGroup() {
    return WringGroup.REFACTOR_INEFFECTIVE;
  }
}