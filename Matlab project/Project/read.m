function A = read(filename)
A = string(missing);
fID = fopen(filename,'r');
line = fgetl(fID);
i = 1;
while ischar(line)
    if ~isempty(line)
       A(i,1) = line;
       i = i+1;
    end
    line = fgetl(fID);
end
fclose(fID);