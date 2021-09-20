import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  productAddedToCart: boolean = false;
  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getProducts();
  }
  getProducts() {
    this.productService.getProducts().subscribe((response) => {
      this.products = response.data;
    });
  }
  addToCart(product: Product) {
    this.productAddedToCart = false;
    this.cartService
      .add({ id: 0, productId: product.id, userId: 1, count: 1 })
      .subscribe((response) => {
        this.productAddedToCart = true;
      });
  }
}
