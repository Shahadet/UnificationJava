# UnificationJava

Implementation of Unification Algorithm in Java
# Instruction to Execute:
 * The program is developed with Java 8. However this code should work with older java version as well.
 * Can be run with command(>javac assign1mha263.java		>java assign1mha263) or with any IDE
 * The program will ask for two separate terms for unification
 * By default the program will show only the final result (with each variable value like prolog)
 * To see the the deduction of each step you need to turn on the log by pressing L or l
 * to exit the program just press e or E

# Example of execution(without log):
    
    This Program works similar to the unify_with_occurs_check(term1,term2) function of prolog
	Please enter Term 1: 
	f(g(h(a(W,W),b(X,Y),t(m))))
	Please enter Term 2: 
	f(g(h(X,b(Z,W),Y)))
	
	W=t(m)
	X=a(t(m),t(m))
	Y=t(m)
	Z=a(t(m),t(m))
	
	yes

   # Example of execution(with log):
 	Type E to exit,L for turning on Log or press enter to continue
	l
	*****Log Printing Enabled*****
	Please enter Term 1: 
	f(g(h(a(W,W),b(X,Y),t(m))))
	Please enter Term 2: 
	f(g(h(X,b(Z,W),Y)))
	Constant or function::[a(W,W)] at-6
	Changing   :X---->a(W,W)
	Updated term1 ->f(g(h(a(W,W),b(a(W,W),Y),t(m))))
	Updated term2 ->f(g(h(a(W,W),b(Z,W),Y)))
	Constant or function::[a(W,W)] at-15
	Changing   :Z---->a(W,W)
	Updated term1 ->f(g(h(a(W,W),b(a(W,W),Y),t(m))))
	Updated term2 ->f(g(h(a(W,W),b(a(W,W),W),Y)))
	Changing   :Y---->W
	Updated term1 ->f(g(h(a(W,W),b(a(W,W),W),t(m))))
	Updated term2 ->f(g(h(a(W,W),b(a(W,W),W),W)))
	Constant or function::[t(m)] at-25
	Changing   :W---->t(m)
	Updated term1 ->f(g(h(a(t(m),t(m)),b(a(t(m),t(m)),t(m)),t(m))))
	Updated term2 ->f(g(h(a(t(m),t(m)),b(a(t(m),t(m)),t(m)),t(m))))
	
	W=t(m)
	X=a(t(m),t(m))
	Y=t(m)
	Z=a(t(m),t(m))
	
	yes

# Other checking :
 * 1. Parenthesis check given. If parenthesis is not well formed program will indicate the place or error. 
 * 2. Variable,constant,function may consist of multiple character.
 * 3. Produce error(indicates position), if function name starts with capital letter.

