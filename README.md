# 30-06-2024---Final-project

# JumlaCycle

## Contact Information

- **Name:** Mohammed Tariq ALghamdi
- **Email:** [mohammedtg209@outlook.com](mailto:mohammedtg208@outlook.com)
- **LinkedIn:** [Mohammed ALghamdi](https://www.linkedin.com/in/mohammed-al-ghamdi-36b470311/)
- **Figma:** [Project Design](https://www.figma.com/design/nNNGhTMIvFlT8xtPiU88oR/JumlaCycle?t=NYOevIxt38XtIUrg-0)
- **Postman Documentation:** [Link](https://documenter.getpostman.com/view/35088433/2sAXqqdNor)
- **Presentation:** [Canva Presentation]([https://www.canva.com/design/DAGQ9ACUzlc/DtIgLE1HbMeTPNDzOfWo_w/edit](https://www.canva.com/design/DAGQ9ACUzlc/DtIgLE1HbMeTPNDzOfWo_w/edit?utm_content=DAGQ9ACUzlc&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton))

## Project Overview

**Project Name:** JumlaCycle

The platform is a marketplace designed to connect suppliers with facilities and customers, enabling bulk transactions of products while promoting sustainable practices through recycling. The platform provides a streamlined solution for buying and selling in large quantities, allowing facilities and suppliers to handle bulk orders, manage pricing negotiations, and facilitate the recycling of materials.

## System Flow

1. **Supplier Lists Products:**
   - Suppliers upload product listings for bulk sales.
   - Listings include product details, minimum order quantity, and price per unit.

2. **Facility Makes a Purchase Request:**
   - A facility submits a request for a specific product in bulk, specifying their desired price and quantity.
   - The request is available for suppliers to view and respond.

3. **Supplier Responds with a Price Offer:**
   - Suppliers review facility requests and submit a price offer based on the requested quantity and price.
   - Suppliers can also provide recyclable materials in response to a facility’s recycling request.

4. **Facility Reviews and Approves/Rejects the Offer:**
   - The facility reviews the supplier’s price offer and either approves or rejects it.
   - If approved, the order is processed, and the transaction is completed.
   - If rejected, the facility can wait for new offers from other suppliers.

5. **Supplier Approves or Rejects Requests:**
   - Suppliers can review requests from facilities and either approve or reject them based on their ability to fulfill the order.
   - This gives suppliers control over which requests they can accept based on stock or logistical capabilities.

## Arabic Summary

المنصة عبارة عن سوق مصمم لربط الموردين بالمرافق والعملاء، مما يتيح إجراء معاملات بالجملة للمنتجات مع تعزيز الممارسات المستدامة من خلال إعادة التدوير. توفر المنصة حلاً مبسطًا للشراء والبيع بكميات كبيرة، مما يسمح للمرافق والموردين بالتعامل مع الطلبات بالجملة وإدارة مفاوضات التسعير وتسهيل إعادة تدوير المواد.

### تدفق النظام:

1. **المورد يعرض المنتجات:**
   - يقوم الموردون بتحميل قوائم المنتجات للبيع بالجملة.
   - تتضمن القوائم تفاصيل المنتج، الحد الأدنى للطلب، وسعر الوحدة.

2. **المنشأة تقدم طلب شراء:**
   - تقدم المنشأة طلباً لمنتج معين بكميات كبيرة، مع تحديد السعر والكمية المطلوبة.
   - يكون الطلب متاحاً للموردين للاطلاع عليه والرد عليه.

3. **المورد يرد بعرض سعر:**
   - يقوم الموردون بمراجعة طلبات المنشآت وتقديم عرض سعر بناءً على الكمية والسعر المطلوبين.
   - يمكن للموردين أيضاً تقديم مواد قابلة لإعادة التدوير استجابةً لطلب إعادة التدوير من المنشأة.

4. **المنشأة تراجع وتوافق/ترفض العرض:**
   - تراجع المنشأة عرض السعر من المورد وتوافق عليه أو ترفضه.
   - إذا تم الموافقة، يتم معالجة الطلب وإتمام المعاملة.
   - إذا تم الرفض، يمكن للمنشأة الانتظار لعروض جديدة من موردين آخرين.

5. **المورد يوافق أو يرفض الطلبات:**
   - يمكن للموردين مراجعة الطلبات من المنشآت ويوافقون أو يرفضونها بناءً على قدرتهم على تلبية الطلب.
   - يمنح ذلك الموردين السيطرة على الطلبات التي يمكنهم قبولها بناءً على المخزون أو القدرات اللوجستية.

## My Work

### Models
- Customer + Customer DTO
- Order
- Review
- Product

### Repository
- Customer
- Order
- Review
- Product

### Services
- Customer
- Order
- Review

### Controller
- Customer
- Order
- Review

### Controller Advice
- ApiException
- MethodArgumentNotValidException
- ConstraintViolationException

### JUnit Tests

#### Repository Tests
- TestFindUserByUserName
- findProductByCategoryTest

#### Service Tests
- getAllProductsTest
- getProductByIdTest
- addProductTest

#### Controller Tests
- getAllCustomers
- testGetProductById
