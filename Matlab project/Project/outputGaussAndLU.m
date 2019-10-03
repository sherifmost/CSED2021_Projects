function outputGaussAndLU(Mat,X,delta,method,equations,results,noOfEq,timeElapsed)
fileID = fopen('Output.txt','at');
fprintf(fileID,'%s\n',method);
for j = 1:size(equations)
    fprintf(fileID,'%s',equations(j) + ' = ' + results(j));
    fprintf(fileID,'\n');
end
fprintf(fileID,'\n');
fprintf(fileID,'%s\n','Augmented Matrix');
for j = 1:size(Mat)
    fprintf(fileID,'%.4f\t   ',Mat(j,:));
    fprintf(fileID,'\n');
end
fprintf(fileID,'\n');
asciiA = double('a');
currChar = char(asciiA);
for i = 2:noOfEq
    fprintf(fileID,'%s             ',currChar);
    currAscii = asciiA +i-1;
    currChar = char(currAscii);
end
fprintf(fileID,'%s',currChar);
fprintf(fileID,'\n');
for l = 1:noOfEq
    fprintf(fileID,'%.4f\t   ',X(l));
end
fprintf(fileID,'\n\n');
fprintf(fileID,'%s\n','delta');
fprintf(fileID,'%.4f\n',delta);
fprintf(fileID,'%s','Time taken: ');
fprintf(fileID,'%d %s\n',timeElapsed,'seconds');
fprintf(fileID,'---------------------------------------------------------------\n');
fclose(fileID);