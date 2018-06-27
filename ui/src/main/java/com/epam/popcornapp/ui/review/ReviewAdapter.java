package com.epam.popcornapp.ui.review;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epam.ui.R;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private final List<ReviewItem> reviews;
    private List<ReviewItem> fullList;

    ReviewAdapter(@NonNull final List<ReviewItem> reviews) {
        this.reviews = reviews;
    }

    List<ReviewItem> getFullList() {
        return fullList;
    }

    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_review_item, parent, false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ReviewHolder holder, final int position) {
        if (reviews.isEmpty()) {
            return;
        }

        holder.authorView.setText(reviews.get(position).getAuthor());
        holder.contentView.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    void setReviews(final List<ReviewItem> reviews) {
        if (reviews == null) {
            return;
        }

        this.fullList = reviews;

        this.reviews.clear();
        if (reviews.size() <= 3) {
            this.reviews.addAll(reviews);
        } else {
            this.reviews.addAll(reviews.subList(0, 3));
        }

        notifyDataSetChanged();
    }

    void withReviews(@NonNull final List<ReviewItem> reviews) {
        this.reviews.addAll(reviews);

        notifyDataSetChanged();
    }

    void clearReviews() {
        this.reviews.clear();

        notifyDataSetChanged();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView authorView;
        ExpandableTextView contentView;

        ReviewHolder(final View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.adapter_review_name_text_view);
            contentView = itemView.findViewById(R.id.adapter_review_content_text_view);

            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    contentView.toggle();
                }
            });

        }
    }
}
