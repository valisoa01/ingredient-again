# PlatsApi

All URIs are relative to *http://localhost:8080/api*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllDishes**](PlatsApi.md#getAllDishes) | **GET** /dishes | Récupère la liste de tous les plats |
| [**updateDishIngredients**](PlatsApi.md#updateDishIngredients) | **PUT** /dishes/{id}/ingredients | Modifie la liste des ingrédients associés à un plat |


<a id="getAllDishes"></a>
# **getAllDishes**
> List&lt;DishResponse&gt; getAllDishes()

Récupère la liste de tous les plats

Retourne une liste d&#39;objets JSON contenant tous les plats enregistrés dans la base de données, avec leurs ingrédients respectifs

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PlatsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api");

    PlatsApi apiInstance = new PlatsApi(defaultClient);
    try {
      List<DishResponse> result = apiInstance.getAllDishes();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PlatsApi#getAllDishes");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;DishResponse&gt;**](DishResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des plats récupérée avec succès |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateDishIngredients"></a>
# **updateDishIngredients**
> DishResponse updateDishIngredients(id, ingredientAssociationRequest)

Modifie la liste des ingrédients associés à un plat

Permet d&#39;associer ou dissocier une liste d&#39;ingrédients au plat spécifié. Les ingrédients fournis qui n&#39;existent pas dans la base sont ignorés.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PlatsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api");

    PlatsApi apiInstance = new PlatsApi(defaultClient);
    Long id = 1L; // Long | Identifiant unique du plat
    List<IngredientAssociationRequest> ingredientAssociationRequest = Arrays.asList(); // List<IngredientAssociationRequest> | Liste des ingrédients à associer ou dissocier du plat
    try {
      DishResponse result = apiInstance.updateDishIngredients(id, ingredientAssociationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PlatsApi#updateDishIngredients");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Long**| Identifiant unique du plat | |
| **ingredientAssociationRequest** | [**List&lt;IngredientAssociationRequest&gt;**](IngredientAssociationRequest.md)| Liste des ingrédients à associer ou dissocier du plat | |

### Return type

[**DishResponse**](DishResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients mise à jour avec succès |  -  |
| **400** | Corps de requête manquant ou invalide |  -  |
| **404** | Plat non trouvé |  -  |
| **500** | Erreur interne du serveur |  -  |

