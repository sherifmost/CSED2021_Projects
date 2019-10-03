function A = polycoeffcients(fun)
f = str2sym(fun);
A = sym2poly(f);
A = A';