package com.brt.xwl.devTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName ViewObjectSwaggerCreator 
 * @Description viewObject 自动添加Swagger註解工具
 * @date 2019年10月05日 
 */
public class EasyControllerSwagger {
	
	/*
	 * 操作步驟：
	 *  1.將本文件放入項目中
	 *  2.修改viewObjectPackageName
	 *  3.運行本文件
	 */
	private static String controllerPackageName = "com.brt.xwl.rest.controller";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println("\n----- View Object Swagger Creator -----\n");
		Long startTime = System.currentTimeMillis();
		generaSwaggerApi();
		Long endTime = System.currentTimeMillis();
		Double spareTime = (double) (endTime - startTime);
		System.out.println("   spareTime time: "+ spareTime/1000 + " s");
		System.out.println("       controller: "+ numbers + " numbers");
		System.out.println("\n---------------------------------------");
	}
	
	private static int numbers = 0;
	
	public static void generaSwaggerApi() throws ClassNotFoundException {
		String filePath = FileUtils.getPackagePathFromName(controllerPackageName);
		//1.get dir
		List<File> controllerFiles = FileUtils.getAllFilesFromPath(filePath);
		if(controllerFiles.isEmpty()) {
			return ;
		}
		for(File controllerFile:controllerFiles) {
			String contents = FileUtils.readContentsFromFile(controllerFile);
			String contentsCopy = new String(contents);
			Class<?> controllerClass = getClassByFileName(controllerFile.getName());
			boolean isControllerClass = isController(controllerClass);
			if(isControllerClass) {
				contentsCopy = addApiToClass(controllerClass,contentsCopy);
				contentsCopy = addApiOperationToMethods(controllerClass,contentsCopy);
				contentsCopy = addApiImplicitParamToMethod(controllerClass,contentsCopy);
				contentsCopy = updateSwaggerApiImport(contentsCopy);
				FileUtils.writeContentsToFile(controllerFile, contentsCopy);
				numbers++;
			}
		}
		
	}
	

	private static boolean isController(Class<?> controllerClass) {
		boolean isController = controllerClass.isAnnotationPresent(Controller.class);
		boolean isRestController = controllerClass.isAnnotationPresent(RestController.class);
		return (isController || isRestController);
	}

	private static Class<?> getClassByFileName(String fileName) throws ClassNotFoundException {
		String className = controllerPackageName+"."+fileName;
		className = className.replace(".java", "");
		Class<?> controllerClass = Class.forName(className);
		return controllerClass;
	}



	private static final String swaggerAnnotationImport = "import io.swagger.annotations";
	
	private static final String apiImport = "import io.swagger.annotations.Api;";
	private static final String apiOperationImport = "import io.swagger.annotations.ApiOperation;";
	private static final String apiImplicitParamImport = "import io.swagger.annotations.ApiImplicitParam;";
	private static final String apiImplicitParamsImport = "import io.swagger.annotations.ApiImplicitParams;";
	
	private static final String api = "@Api";
	private static final String apiOperation = "@ApiOperation";
	private static final String apiImplicitParam = "@ApiImplicitParam";
	private static final String apiImplicitParams = "@ApiImplicitParams";
	
	/**
	 * @author xuanweilun   
	 * @date 2019年10月14日 下午5:32:40 
	 * @Title: updateSwaggerApiImport 
	 * @Description: 导入包
	 * @param contents
	 * @return String
	 * @throws
	 */
	private static String updateSwaggerApiImport(String contents) {
		//1.update apiImport
		contents = updateImport(contents,apiImport,api);
		//2.update apiOperationImport
		contents = updateImport(contents,apiOperationImport,apiOperation);
		//3.update apiImplicitParamImport
		contents = updateImport(contents,apiImplicitParamImport,apiImplicitParam);
		//4.update apiImplicitParamsImport
		contents = updateImport(contents,apiImplicitParamsImport,apiImplicitParams);
		return contents;
	}

	private static String updateImport(String contents,String importFile,String annotation) {
		int apiOperationImportIndex = contents.indexOf(importFile);
		int apiOperationIndex = contents.indexOf(annotation);
		//if api not exist and import exsit 
		if(-1 == apiOperationIndex && -1 != apiOperationImportIndex) {
			contents = contents.replace(importFile,"");
		}
		//if api exist and import not exist add import
		if(-1 != apiOperationIndex && -1 == apiOperationImportIndex) {
			String lastSwaggerImport = getLastSwaggerImport(contents,swaggerAnnotationImport);
			//if
			if(null != lastSwaggerImport) {
				contents = contents.replace(lastSwaggerImport,lastSwaggerImport+"\n"+importFile);
			}else {
				int indexOfPackageEnd = contents.indexOf(";");
				if(-1 != indexOfPackageEnd) {
					String packageName = contents.substring(0,indexOfPackageEnd+1);
					contents = contents.replace(packageName,packageName+"\n\n" + importFile);
				}
				
			}
		}
		return contents;
	}

	private static String getLastSwaggerImport(String contents, String importType) {
		int lastImportIndex = contents.lastIndexOf((importType));
		if(-1 == lastImportIndex) {
			return null;
		}
		String subContent = contents.substring(lastImportIndex);
		int indexOfend = subContent.indexOf(";");
		String lastSwaggerImport = subContent.substring(0,indexOfend+1);
		return lastSwaggerImport;
	}


	/**
	 * @author xuanweilun   
	 * @date 2019年10月14日 下午5:33:16 
	 * @Title: addApiImplicitParamToMethod 
	 * @Description: 添加注解到方法
	 * @param voClass
	 * @param contents
	 * @return String
	 * @throws
	 */
	private static String addApiImplicitParamToMethod(Class<?> voClass, String contents) {
		for(Method method:voClass.getDeclaredMethods()) 
		{
			if(!method.isAnnotationPresent(RequestMapping.class)
					&& !method.isAnnotationPresent(GetMapping.class)
					&& !method.isAnnotationPresent(PutMapping.class)
					&& !method.isAnnotationPresent(DeleteMapping.class))
				continue;
			if(method.isAnnotationPresent(ApiImplicitParams.class) || method.isAnnotationPresent(ApiImplicitParam.class))
				continue;
			contents = insertApiImplicitParamToMethod(method,contents);
		}
		return contents;
	}



	private static String insertApiImplicitParamToMethod(Method method, String contents) {
		Parameter[] parameters = method.getParameters();
		//no parameter
		if(0 == parameters.length)
		{
			return contents;
		}
		//one requestBody parameter
		String methodTypeName = getReturnType(method);
		String oldMethod = "\tpublic " + methodTypeName + method.getName();
		if(1 == parameters.length)
		{
			Parameter parameter = parameters[0];
			boolean isRequestBodyAnnotation = parameter.isAnnotationPresent(RequestBody.class);
			if(isRequestBodyAnnotation) return contents;
			//one path or query parameter
			String parameterApi = getParameterApi(parameter);
			contents = contents.replace(oldMethod,parameterApi +"\n"+ oldMethod);
			return contents;
		}else {
			List<String> parameterApis = new ArrayList<String>();
			for(Parameter parameter :parameters) {
				if(parameter.isAnnotationPresent(RequestBody.class)) 
					continue;
				//one path or query parameter
				String parameterApi = getParameterApi(parameter);
				parameterApis.add(parameterApi);
			}
			if(1 == parameterApis.size()) {
				contents = contents.replace(oldMethod,parameterApis.remove(0) +"\n"+ oldMethod);
				return contents;
			}
			String parameterApiss = "\t@ApiImplicitParams({\n";
			for(String parameterApi:parameterApis) {
				parameterApiss = parameterApiss +"\t"+ parameterApi + ",\n";
			}
			parameterApiss = parameterApiss.substring(0,parameterApiss.lastIndexOf(",")) + "\n\t\t})";
			contents = contents.replace(oldMethod,parameterApiss +"\n"+ oldMethod);
			
		}
		return contents;
	}


	private static String getParameterApi(Parameter parameter) {
		boolean isPathVariablePresent = parameter.isAnnotationPresent(PathVariable.class);
		boolean isRequestParamPresent = parameter.isAnnotationPresent(RequestParam.class);
		String parameterType = getParameterTypeName(parameter);
		String parameterApi = "";
		if(isPathVariablePresent) {
			parameterApi = "\t@ApiImplicitParam(paramType=\"path\","
					+ "name=\""+parameter.getName()+"\",value=\"\""
					+ ",dataType=\""+parameterType+"\",required=true)";
		}else if(isRequestParamPresent) {
			parameterApi = "\t@ApiImplicitParam(paramType=\"query\","
					+ "name=\""+parameter.getName()+"\",value=\"\""
					+ ",dataType=\""+parameterType+"\",required=false)";
		}
		return parameterApi;
		
	}


	private static String getParameterTypeName(Parameter parameter) {
		Class<?> type = parameter.getType();
		Type fieldType = parameter.getParameterizedType();
		String typeSimepleName = "";
		try{//泛型属性
			ParameterizedType pt = (ParameterizedType)fieldType;
			Type t1 =  pt.getActualTypeArguments()[0];
			String simpleTypeName = t1.getTypeName().substring(t1.getTypeName().lastIndexOf(".")+1);
			typeSimepleName = type.getSimpleName()+ "<"+simpleTypeName+"> ";
		}catch(Exception e) {
			//普通类型
			typeSimepleName = type.getSimpleName();
		}
		return typeSimepleName;
	}


	private static String getReturnType(Method method) {
		Class<?> type = method.getReturnType();
		Type fieldType = method.getGenericReturnType();
		String typeName = "";
		try{//泛型属性
			ParameterizedType pt = (ParameterizedType)fieldType;
			Type t1 =  pt.getActualTypeArguments()[0];
			typeName = t1.getTypeName().substring(t1.getTypeName().lastIndexOf(".")+1);
			typeName = type.getSimpleName()+ "<"+typeName+"> ";
		}catch(Exception e) {
			//普通类型
			typeName = type.getSimpleName()+ " ";
		}
		return typeName;
	}


	private static String addApiOperationToMethods(Class<?> voClass, String contents) {
		Method[] methods = voClass.getDeclaredMethods();
		//2.add apiOperation to method
		for(Method method:methods) {
			if(method.isAnnotationPresent(ApiOperation.class)) 
				continue;
			if(method.isAnnotationPresent(RequestMapping.class) 
					|| method.isAnnotationPresent(GetMapping.class)
					|| method.isAnnotationPresent(PutMapping.class)
					|| method.isAnnotationPresent(DeleteMapping.class)) {
				String typeName = getReTurnTypeByMethod(method);
				String oldMethod = "\tpublic " + typeName+ " " + method.getName();
				contents = contents.replace(oldMethod, "\t@ApiOperation(value=\"\")\n"+oldMethod);
			}
		}
		return contents;
	}


	private static String getReTurnTypeByMethod(Method method) {
		Class<?> type = method.getReturnType();
		Type fieldType = method.getGenericReturnType();
		String typeName = "";
		try{//泛型属性
			ParameterizedType pt = (ParameterizedType)fieldType;
			Type t1 =  pt.getActualTypeArguments()[0];
			typeName = t1.getTypeName().substring(t1.getTypeName().lastIndexOf(".")+1);
			typeName = type.getSimpleName()+ "<"+typeName+"> ";
		}catch(Exception e) {
			//普通类型
			typeName = type.getSimpleName();
		}
		return typeName;
	}


	private static String addApiToClass(Class<?> controllerClass, String contents) {
		Boolean isApi = controllerClass.isAnnotationPresent(Api.class);
		if(!isApi) {
			String oldClassName = "public class "+controllerClass.getSimpleName();
			String tags = "@Api(tags=\""+controllerClass.getSimpleName()+"\")\n";
			contents = contents.replace(oldClassName, tags+ oldClassName);
		}
		return contents;
	}


	
}

class FileUtils {

	/**
	 * @author xuanweilun   
	 * @date 2019年10月15日 上午9:15:18 
	 * @Description: 写内容到文件上
	 */
	public static void writeContentsToFile(File outputFile,String contents) {
		try {
			OutputStream out = new FileOutputStream(outputFile);
			out.write(contents.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("文件写出失败！路径="+outputFile.getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	/**
	 * @author xuanweilun   
	 * @date 2019年10月15日 上午9:15:23 
	 * @Description: 从文件中读取内容
	 */
	public static String readContentsFromFile(File inputFile) {
		byte bs[] = null;
		try {
			InputStream in = new FileInputStream(inputFile);
			bs = new byte[(int)inputFile.length()]; 
			in.read(bs);
			in.close();
		} catch (IOException e) {
			System.err.println("文件读取失败！路径="+inputFile.getAbsolutePath());
			e.printStackTrace();
		}
		String content = new String(bs);
		return content;
	}
	
	/**
	 * @author xuanweilun   
	 * @date 2019年12月19日 下午12:44:42 
	 * @Description: 获取指定路径下的所有文件。除了目录
	 */
	public static List<File> getAllFilesFromPath(String filePath){
		File dir = new File(filePath);
		List<File> files = new ArrayList<File>();
		if(!dir.isDirectory()) {
			files.add(dir);
			return files;
		}
		getFilesByDir(dir,files);
		return files;
	}

	/**
	 * @author xuanweilun   
	 * @date 2019年12月19日 下午12:44:47 
	 * @Description: 获取文件夹的所有文件
	 */
	private static void getFilesByDir(File dir, List<File> files) {
		for (File file:dir.listFiles()) {
			if(file.isDirectory()) {
				getFilesByDir(file,files);
			}else {
				files.add(file);
			}
		}
	}
	
	/**
	 * @author xuanweilun   
	 * @date 2019年12月24日 下午3:24:45 
	 * @Description: 获得指定包名的file路径
	 */
	public static String getPackagePathFromName(String packageName) {
		String projectDir = System.getProperty("user.dir");
		String packagePath = packageName.replace(".", "\\");
		packagePath = projectDir+ "\\src\\main\\java\\" + packagePath;
		return packagePath;
	}
	
	
}

