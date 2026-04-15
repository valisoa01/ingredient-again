# IngrdientsApi

All URIs are relative to *http://localhost:8080/api*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllIngredients**](IngrdientsApi.md#getAllIngredients) | **GET** /ingredients | Récupère la liste de tous les ingrédients |
| [**getIngredientById**](IngrdientsApi.md#getIngredientById) | **GET** /ingredients/{id} | Récupère un ingrédient par son identifiant |
| [**getIngredientStock**](IngrdientsApi.md#getIngredientStock) | **GET** /ingredients/{id}/stock | Récupère la valeur du stock d&#39;un ingrédient à un moment donné |


<a id="getAllIngredients"></a>
# **getAllIngredients**
> List&lt;IngredientResponse&gt; getAllIngredients()

Récupère la liste de tous les ingrédients

Retourne une liste d&#39;objets JSON contenant tous les ingrédients enregistrés dans la base de données

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.IngrdientsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api");

    IngrdientsApi apiInstance = new IngrdientsApi(defaultClient);
    try {
      List<IngredientResponse> result = apiInstance.getAllIngredients();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IngrdientsApi#getAllIngredients");
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

[**List&lt;IngredientResponse&gt;**](IngredientResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des ingrédients récupérée avec succès |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getIngredientById"></a>
# **getIngredientById**
> IngredientResponse getIngredientById(id)

Récupère un ingrédient par son identifiant

Retourne un objet JSON correspondant à l&#39;ingrédient portant l&#39;identifiant fourni

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.IngrdientsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api");

    IngrdientsApi apiInstance = new IngrdientsApi(defaultClient);
    Long id = 1L; // Long | Identifiant unique de l'ingrédient
    try {
      IngredientResponse result = apiInstance.getIngredientById(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IngrdientsApi#getIngredientById");
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
| **id** | **Long**| Identifiant unique de l&#39;ingrédient | |

### Return type

[**IngredientResponse**](IngredientResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ingrédient trouvé |  -  |
| **404** | Ingrédient non trouvé |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getIngredientStock"></a>
# **getIngredientStock**
> StockResponse getIngredientStock(id, at, unit)

Récupère la valeur du stock d&#39;un ingrédient à un moment donné

Retourne un objet JSON contenant la valeur du stock de l&#39;ingrédient à la date/heure spécifiée

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.IngrdientsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api");

    IngrdientsApi apiInstance = new IngrdientsApi(defaultClient);
    Long id = 1L; // Long | Identifiant unique de l'ingrédient
    OffsetDateTime at = OffsetDateTime.parse("2025-04-15T10:30:00Z"); // OffsetDateTime | Date et heure à laquelle on veut connaître la valeur du stock
    String unit = "PCS"; // String | Unité de mesure du stock (PCS, KG, L)
    try {
      StockResponse result = apiInstance.getIngredientStock(id, at, unit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling IngrdientsApi#getIngredientStock");
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
| **id** | **Long**| Identifiant unique de l&#39;ingrédient | |
| **at** | **OffsetDateTime**| Date et heure à laquelle on veut connaître la valeur du stock | |
| **unit** | **String**| Unité de mesure du stock (PCS, KG, L) | [enum: PCS, KG, L] |

### Return type

[**StockResponse**](StockResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Valeur du stock récupérée avec succès |  -  |
| **400** | Paramètres de requête manquants |  -  |
| **404** | Ingrédient non trouvé |  -  |
| **500** | Erreur interne du serveur |  -  |

