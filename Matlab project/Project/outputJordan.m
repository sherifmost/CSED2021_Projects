function outputJordan(X,flag,method,equations,results,noOfEq,timeElapsed)
fileID = fopen('Output.txt','at');
fprintf(fileID,'%s\n',method);
for j = 1:size(equations)
    fprintf(fileID,'%s',equations(j) + ' = ' + results(j));
    fprintf(fileID,'\n');
end
fprintf(fileID,'\n');
if flag == 1
    asciiA = double('a');
    currChar = char(asciiA);
    for i = 2:noOfEq
        fprintf(fileID,'%s\t\t   ',currChar);
        currAscii = asciiA +i-1;
        currChar = char(currAscii);
    end
    fprintf(fileID,'%s',currChar);
    fprintf(fileID,'\n');
    for l = 1:noOfEq
        fprintf(fileID,'%.4f\t   ',X(l));
    end
    fprintf(fileID,'\n\n');
else
    fprintf(fileID,'%s\n','No Solution');
end
fprintf(fileID,'%s','Time taken: ');
fprintf(fileID,'%d %s\n',timeElapsed,'seconds');
fprintf(fileID,'---------------------------------------------------------------\n');
fclose(fileID);