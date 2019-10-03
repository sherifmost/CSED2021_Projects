%converts string into function form 
function fun = conv2fun(str)
y = exp(1);
strdb = sprintf('%f',y);
str = strrep(str,'e',strdb);
fun = str2func(['@(x)' str]);
end