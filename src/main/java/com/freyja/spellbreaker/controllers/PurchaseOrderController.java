package com.freyja.spellbreaker.controllers;

import com.freyja.spellbreaker.entities.PartsIndividual;
import com.freyja.spellbreaker.entities.PoItem;
import com.freyja.spellbreaker.entities.PurchaseOrder;
import com.freyja.spellbreaker.repos.POItemRepository;
import com.freyja.spellbreaker.repos.PartsRepository;
import com.freyja.spellbreaker.repos.PurchaseOrderRepository;
import com.freyja.spellbreaker.repos.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Controller
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Autowired
    private POItemRepository poItemRepo;

    @Autowired
    private SKURepository skuRepo;

    @Autowired
    private PartsRepository partsRepo;

    @GetMapping("/purchase-orders")
    public String purchaseOrder(Model model) {

        List<PurchaseOrder> purchaseOrders = (List<PurchaseOrder>) purchaseOrderRepo.findAll();

        purchaseOrders.sort(Comparator.comparing(PurchaseOrder::getTimestamp).reversed());

        model.addAttribute("purchaseOrders", purchaseOrders);

        return "purchase-orders";
    }

    @GetMapping("/po-details/{poId}")
    public String createPurchaseOrder(Model model, @PathVariable(required = false) Integer poId) {
        PurchaseOrder po = purchaseOrderRepo.findById(poId).get();

        model.addAttribute("po", po);
        model.addAttribute("parts", poItemRepo.findAllByPoId(poId));
        return "po-details";
    }

    @PostMapping("/create-po")
    public String createPurchaseOrder(String retailer, String orderNo) {
        PurchaseOrder po = new PurchaseOrder();

        po.setTimestamp(Instant.now());
        po.setRetailer(retailer);
        po.setStatus("Not finalized");
        po.setOrderNo(orderNo);
        po.setTotalPrice(0.00);
        purchaseOrderRepo.save(po);

        return "redirect:/purchase-orders";
    }

    @PostMapping("/add-part-po")
    public String addPartToPO(Integer poId, String partNumber, String quantity, Double price, String partName) {

        PoItem poItem = new PoItem();
        poItem.setPo(purchaseOrderRepo.findById(poId).get());
        poItem.setSku(skuRepo.findById(Integer.valueOf(partNumber)).get());
        poItem.getSku().setPartName(partName);
        poItem.setQuantity(Integer.valueOf(quantity));
        poItem.setPrice(BigDecimal.valueOf(price));
        poItemRepo.save(poItem);
        return "redirect:/po-details/" + poId;
    }

    @PostMapping("/delete-po/{poId}")
    public String deletePO(@PathVariable(required = false) Integer poId) {
        purchaseOrderRepo.deleteById(poId);
        return "redirect:/purchase-orders";
    }

    @PostMapping("/remove-part/")
    public String removePart(Integer poId, Integer partId) {
        poItemRepo.deleteById(partId);
        return "redirect:/po-details/" + poId;
    }

    @PostMapping("/finalize-po/{poId}")
    public String finalizePO(@PathVariable(required = false) Integer poId) {
        PurchaseOrder po = purchaseOrderRepo.findById(poId).get();
        po.setStatus("Finalized");
        purchaseOrderRepo.save(po);

        List<PoItem> parts = poItemRepo.findAllByPoId(poId);
        for (PoItem part : parts) {
            PartsIndividual partsIndividual = new PartsIndividual();
            partsIndividual.setSku(part.getSku());
            partsIndividual.getSku().setQuantity(part.getQuantity() + partsIndividual.getSku().getQuantity());
            partsIndividual.setPrice(part.getPrice());
            partsRepo.save(partsIndividual);

            po.setTotalPrice(po.getTotalPrice() + (part.getPrice().doubleValue() * part.getQuantity()));
        }
        purchaseOrderRepo.save(po);

        return "redirect:/purchase-orders";
    }

}
