function fileReader(fileID,fileName,prevLine,handles)
global result;
if (isempty(prevLine))
    fileID = fopen(fileName,'r');
    Eq = fgetl(fileID);
    noOfEq = str2num(Eq);
else
    noOfEq = str2num(prevLine);
end
%fprintf('noOfEq : %d\n',noOfEq);
for i = 1:noOfEq
    eqn = getEqn(string(fgetl(fileID)),i);
    if i == 1
        equations = eqn;
    else
        equations = [equations;eqn];
    end
end
method = fgetl(fileID);
%fprintf('The method is : %s\n',method);
if strcmpi(method,'Gauss-Seidel')
    initialGuesses = string(fgetl(fileID));
    maxIterations = str2double(fgetl(fileID));
    precision = str2num(fgetl(fileID));
    initials = split(initialGuesses);
    for i = 1:length(initials)
        iG = initials(i);
        if i == 1
            initialGuesses = str2double(iG);
        else
            initialGuesses = [initialGuesses;str2double(iG)];
        end
    end
    tic;
    [X,Error,Final,Iterations] = gaussSeidel(noOfEq,equations,result,initialGuesses,precision,maxIterations);
    timeElapsed = toc;
    plotGaussSeidel(X,Iterations,handles);
    outputSeidel(X,Error,Final,Iterations,method,equations,result,noOfEq,timeElapsed);
elseif strcmpi(method,'Gaussian-elimination')
    tic;
    [M,X,delta] = gaussianElemination(noOfEq,equations,result);
    timeElapsed = toc;
    outputGaussAndLU(M,X,delta,method,equations,result,noOfEq,timeElapsed);
elseif strcmpi(method,'LU decomposition')
    tic;
    [LU,X,delta] = LUDecomposition(noOfEq,equations,result);
    timeElapsed = toc;
    outputGaussAndLU(LU,X,delta,method,equations,result,noOfEq,timeElapsed);
elseif strcmpi(method,'gaussian-jordan')
    tic;
    [X,flag] = gaussJordan(noOfEq,equations,result);
    timeElapsed = toc;
    outputJordan(X,flag,method,equations,result,noOfEq,timeElapsed);
end
nextLine = fgetl(fileID);
if nextLine ~= -1
    fileReader(fileID,fileName,nextLine,handles);
end
fileID = fopen(fileName,'r');
fclose(fileID);

function eqn = getEqn(eq,i)
global result;
eqn = split(eq);
if ~isletter(eqn(end))
    res = eqn(end-1) + eqn(end);
    eqn = join(eqn);
    newEqn = "";
    for j = 1:strlength(eqn)-4
        newEqn = newEqn + eqn{1}(j);
    end
    res = -str2double(res);
    if i == 1
        result = res;
    else
        result = [result;res];
    end
    eqn = string(cellstr(newEqn));
    eqn = regexprep(eqn, '\s+', '');
else
    res = str2double(0);
    result = [result;res];
    eqn = string(cellstr(join(eqn)));
    eqn = regexprep(eqn, '\s+', '');
end