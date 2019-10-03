s = "+6";
ss = "-6";
n = -str2num(ss);
eqn = "2*a + 2*b - 7";
disp(eqn);
eqnn = '2*a + 2*b - 7';
%eqn = regexprep(eqn, '\s+', '');
%eqnn = eqnn(~isspace(eqnn));
%eqn = eqn(eqn ~= ' ');
%disp(eqnn);
%disp(ss);
eqn = split(eqn);
disp(length(eqn));
disp(eqn);
res = eqn(end-1) + eqn(end);
eqn = join(eqn);
%disp(eqn);
newEqn = "";
for i = 1:strlength(eqn)-4
    newEqn = newEqn + eqn{1}(i);
    
end
res = -str2double(res);
eqn = cellstr(newEqn);
%disp(eqn);
%disp(res);
a=[6;7;-4];
b=["3*a + 2*b + c";"2*a + 3*b";"2*c"];
c=[b a];
%disp(c);

%x = [1:1:6];
%y = 7*x.^2+x;
%plot(x, y);

%fid = fopen('Input.txt','rt');
%data = fscanf(fid,'%s');
%disp(data(3));
%fclose(fid);

%file = uigetfile;
%disp(file);